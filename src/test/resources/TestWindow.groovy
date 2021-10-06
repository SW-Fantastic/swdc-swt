import org.eclipse.jface.dialogs.MessageDialog
import org.eclipse.swt.SWT
import org.eclipse.ui.forms.widgets.Section
import org.swdc.dsl.TestCell
import org.swdc.dsl.TestController
import org.swdc.swt.ViewController
import org.swdc.swt.ViewRequire
import org.swdc.swt.layouts.SWTBorderLayout
import org.swdc.swt.layouts.SWTFormData
import org.swdc.swt.layouts.SWTFormLayout
import org.swdc.swt.layouts.SWTGridLayout
import org.swdc.swt.layouts.SWTRowLayout
import org.swdc.swt.layouts.SWTStackLayout
import org.swdc.swt.widgets.SWTButton
import org.swdc.swt.widgets.SWTCComboBox
import org.swdc.swt.widgets.SWTCLabel
import org.swdc.swt.widgets.SWTCanvas
import org.swdc.swt.widgets.SWTComboBox
import org.swdc.swt.widgets.SWTCoolBar
import org.swdc.swt.widgets.SWTCoolItem
import org.swdc.swt.widgets.SWTDateTime
import org.swdc.swt.widgets.SWTLabel
import org.swdc.swt.widgets.SWTLink
import org.swdc.swt.widgets.SWTList
import org.swdc.swt.widgets.SWTProgressBar
import org.swdc.swt.widgets.SWTScale
import org.swdc.swt.widgets.SWTSlider
import org.swdc.swt.widgets.SWTSpinner
import org.swdc.swt.widgets.SWTStyledText
import org.swdc.swt.widgets.SWTTable
import org.swdc.swt.widgets.SWTTableColumn
import org.swdc.swt.widgets.SWTText
import org.swdc.swt.widgets.SWTToolBar
import org.swdc.swt.widgets.SWTToolItem
import org.swdc.swt.widgets.SWTTree
import org.swdc.swt.widgets.SWTWidget
import org.swdc.swt.widgets.Stage
import org.swdc.swt.widgets.base.SWTView
import org.swdc.swt.widgets.form.SWTForm
import org.swdc.swt.widgets.form.SWTFormExpandPane
import org.swdc.swt.widgets.form.SWTFormHyperLink
import org.swdc.swt.widgets.form.SWTFormSection
import org.swdc.swt.widgets.form.SWTFormText
import org.swdc.swt.widgets.menu.SWTMenu
import org.swdc.swt.widgets.menu.SWTMenuItem
import org.swdc.swt.widgets.pane.SWTCBanner
import org.swdc.swt.widgets.pane.SWTCTab
import org.swdc.swt.widgets.pane.SWTCTabPane
import org.swdc.swt.widgets.pane.SWTExpandBar
import org.swdc.swt.widgets.pane.SWTExpandItem
import org.swdc.swt.widgets.pane.SWTGroup
import org.swdc.swt.widgets.pane.SWTPane
import org.swdc.swt.widgets.pane.SWTSashForm
import org.swdc.swt.widgets.pane.SWTScrollPane
import org.swdc.swt.widgets.pane.SWTTab
import org.swdc.swt.widgets.pane.SWTTabPane
import org.swdc.swt.widgets.pane.SWTViewForm

@ViewRequire(value = "MountDemo")
@ViewController(TestController.class)
class SWTWindow extends SWTView {


    private demoTable() {
        SWTTable.table(SWT.NORMAL | SWT.FULL_SELECTION).define {

            data Arrays.asList(
                    new TestCell("aaa", "bbb"),
                    new TestCell("1234", "cdef")
            )

            autoColumnWidth true

            layout SWTGridLayout.cell().define {
                colSpan 6
                fillWidth true
                fillHeight false
                rowAlignment SWT.FILL
                columnAlignment SWT.CENTER
            }
            children widget {
                SWTTableColumn.tableColumn(SWT.CANCEL).define {
                    text  "Test Col"
                    width 120
                    factory (TestCell obj) -> obj.getName()
                }
            } >> widget {
                SWTTableColumn.tableColumn(SWT.CANCEL).define {
                    text "Col"
                    width 120
                    factory (TestCell obj) -> obj.getProp()
                }
            }
        }
    }

    @Override
    protected SWTWidget viewPage() {

     /*   layout SWTGridLayout.gridLayout().define {
            margin 6,6
            spacing 8,8
            columns 6
        } */

        /* children */
        widget {
            SWTLabel.label(SWT.NORMAL,"Test Label").define {
                text "change"
                color "#66CCFF"
                background "#E6E6E6"
                id "lblTest"
                layout SWTGridLayout.cell().define {
                    colSpan 2
                    rowAlignment SWT.FILL
                }
            }
        } >> widget {
            SWTText.textView(SWT.NORMAL|SWT.MULTI|SWT.V_SCROLL, "Test \nText").define {
                size 120,60
                layout SWTGridLayout.cell().define {
                    colSpan 2
                    rowSpan 2
                    fillWidth true
                    rowAlignment SWT.FILL
                    columnAlignment SWT.FILL
                }
            }
        } >> widget {
            SWTButton.button(SWT.NONE).define {
                text "Test Button"
                onAction "hello"
            }
        } >> widget {
            SWTButton.button(SWT.NORMAL).define {
                text "Test Dialog"
                onAction {
                    MessageDialog.openInformation(
                            getStage().getWidget(),
                            "标题",
                            "窗口显示的内容。"
                    )
                }
            }
        } >> widget {
            demoTable()
        } >> widget {
            SWTComboBox.comboBox(SWT.NORMAL).define {

                id "comb"

                data Arrays.asList("Test OptionA", "Test OptionB")

                factory (Object obj) -> obj.toString()

                layout SWTGridLayout.cell().define {
                    colSpan 6
                    fillWidth true
                    rowAlignment SWT.FILL
                }

                select "Test OptionA"

            }
        } >> widget {
            SWTTabPane.tabPane(SWT.NORMAL).define {

                layout SWTGridLayout.cell().define {
                    colSpan 6
                    rowSpan 4
                    fillHeight true
                    fillWidth true
                    columnAlignment SWT.FILL
                    rowAlignment SWT.FILL
                    width 120
                    height 120
                }


                menu new SWTMenu(SWT.POP_UP).item(new SWTMenuItem(SWT.PUSH).define {
                    text "test"
                })

                children widget {
                    SWTTab.tab(SWT.FLAT, "coolbar").define {
                        children SWTPane.pane(SWT.NONE).define {
                            layout SWTRowLayout.rowLayout(SWT.VERTICAL)
                            children new SWTCoolBar(SWT.FLAT).define {
                                children new SWTCoolItem(SWT.NONE).define {
                                    control SWTButton.button(SWT.FLAT)
                                            .define {
                                                text "Cool item"
                                                size 80,24
                                            }
                                }
                            }
                        }
                    }
                } >> widget{
                    SWTTab.tab(SWT.FLAT,"Stack").define {
                        children SWTPane.pane(SWT.FLAT).define {
                            id "stack"
                            layout new SWTStackLayout()
                            children widget {
                                SWTButton.button(SWT.FLAT).define {
                                    id "btnA"
                                    text "Test A"
                                }
                            } >> widget {
                                SWTButton.button(SWT.FLAT).define {
                                    id "btnB"
                                    text "Test B"
                                }
                            }
                        }
                    }

                } >> widget {
                    SWTTab.tab(SWT.NORMAL,"Tab 0").define {
                        children widget {
                            SWTButton.button(SWT.NORMAL).define{
                                text "Test Tab"
                            }
                        }
                    }
                } >> widget {
                    SWTTab.tab(SWT.NORMAL,"Tab 1").define {

                        text "Tab 1"

                        children widget {
                            SWTPane.pane(SWT.NORMAL).define {
                                layout SWTBorderLayout.borderLayout()
                                children widget {
                                    new SWTToolBar(SWT.FLAT|SWT.HORIZONTAL).define {
                                        background "#CECECE"
                                        layout SWTBorderLayout.top(32)
                                        children widget {
                                            new SWTToolItem(SWT.PUSH).define {
                                                text "Tool"
                                            }
                                        } >> widget {
                                            new SWTToolItem(SWT.SEPARATOR).define {
                                                size 120,SWT.DEFAULT
                                                control SWTButton.button(SWT.PUSH).define {
                                                    text "Test"
                                                }
                                            }
                                        }
                                    }
                                } >> widget {
                                    SWTButton.button(SWT.NORMAL).define {
                                        text "BD 1"
                                        layout SWTBorderLayout.bottom(60)
                                    }
                                }
                            }
                        }
                    }
                } >> widget {
                    SWTTab.tab(SWT.NORMAL,"List") .define {
                        children widget {
                            SWTList.list(SWT.NORMAL).define {
                                factory((String o) -> o.toString())
                                data Arrays.asList("Test A", "Test B", "Test C")
                            }
                        }
                    }
                } >> widget {
                    SWTTab.tab(SWT.NORMAL, "control A").define {
                        children SWTPane.pane(SWT.NORMAL).define {
                            layout SWTRowLayout.rowLayout(SWT.HORIZONTAL)
                            children widget {
                                SWTSpinner.spinner(SWT.NORMAL).define {
                                    layout SWTRowLayout.cell().define {
                                        size 80,SWT.DEFAULT
                                    }
                                }
                            } >> widget {
                                SWTDateTime.dateTime(SWT.NORMAL).define {
                                    layout SWTRowLayout.cell().define {
                                        size 80,SWT.DEFAULT
                                    }
                                }
                            } >> widget {
                                SWTScale.scale(SWT.NORMAL).define {
                                    layout SWTRowLayout.cell().define {
                                        size 80,SWT.DEFAULT
                                    }
                                }
                            } >> widget {
                                SWTSlider.slider(SWT.NORMAL).define {
                                    layout SWTRowLayout.cell().define {
                                        size 80,SWT.DEFAULT
                                    }
                                }
                            } >> widget {
                                SWTLink.link(SWT.NORMAL).define {
                                    text "link text"
                                    layout SWTRowLayout.cell().define {
                                        size 80,SWT.DEFAULT
                                    }
                                }
                            } >> widget {
                                new SWTProgressBar(SWT.FILL)
                            } >> widget {
                                new SWTCanvas(SWT.FLAT|SWT.BORDER)
                            } >> widget {
                                getLoader().create("MountDemo").define {
                                    id "demo"
                                }
                            } >> widget {
                                new SWTCLabel(SWT.DEFAULT).define {
                                    text "Test CLabel"
                                }
                            } >> widget {
                                new SWTCComboBox(SWT.FLAT).define {
                                    text "ccombo"
                                }
                            }>> widget {
                                new SWTStyledText(SWT.BORDER)
                            }
                        }
                    }
                } >> widget {
                    SWTTab.tab(SWT.NORMAL,"Scroll").define {
                        children widget {
                            SWTScrollPane.scrollPane(SWT.H_SCROLL|SWT.V_SCROLL).define {
                                size 120,120
                                children widget {
                                    SWTPane.pane(SWT.NORMAL).define {
                                        size 1000,1000
                                        layout SWTGridLayout.gridLayout().define {
                                            columns 8
                                        }
                                    }
                                }
                            }
                        }
                    }
                } >> widget {
                    SWTTab.tab(SWT.NORMAL,"Tree").define {
                        children widget{
                            new SWTTree(SWT.NORMAL)
                        }
                    }
                } >> widget {
                    SWTTab.tab(SWT.NORMAL,"Form Section").define {
                        children widget{
                            SWTFormSection.section(Section.TITLE_BAR|Section.TWISTIE).define {
                                text "Title"
                                expand true
                                children widget {
                                    SWTPane.pane(SWT.NORMAL).define {
                                        layout SWTGridLayout.gridLayout().define {
                                            columns 4
                                        }
                                        children widget {
                                            SWTLabel.label(SWT.NORMAL, "Test Sec")
                                        } >> widget {
                                            SWTButton.button(SWT.NORMAL).define {
                                                text "Frm Button"
                                                onAction {
                                                    MessageDialog.openInformation(
                                                            getStage().getWidget(),
                                                            "Frm Button",
                                                            "clicked"
                                                    )
                                                }
                                            }
                                        } >> widget{
                                            SWTFormHyperLink.hyperLink(SWT.NORMAL).define {
                                                text "Hyper Link"
                                                onAction {
                                                    MessageDialog.openInformation(
                                                             getStage().getWidget(),
                                                            "HyperLink",
                                                            "HyperLink 被激活。。"
                                                    )
                                                }
                                            }
                                        } >> widget {
                                            SWTText.textView(SWT.NORMAL,"Text").define {
                                                layout SWTGridLayout.cell().define {
                                                    colSpan 4
                                                    rowAlignment SWT.FILL
                                                    fillWidth true
                                                }
                                            }
                                        } >>widget {
                                            new SWTFormText(SWT.NORMAL).define {
                                                parseTag false
                                                text "测试文本"
                                            }
                                        } >> widget {
                                            demoTable()
                                        } >> widget {
                                            SWTFormExpandPane.expandPane(Section.TWISTIE).define {
                                                text "Expand Pane"
                                                layout SWTGridLayout.cell().define {
                                                    colSpan 4
                                                    rowSpan 2
                                                    rowAlignment SWT.FILL
                                                    columnAlignment SWT.FILL
                                                    fillHeight true
                                                    fillWidth true
                                                }
                                                children widget {
                                                    demoTable()
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } >> widget {
                    SWTTab.tab(SWT.NORMAL,"Form").define {
                        children widget {
                            new SWTForm(SWT.NORMAL).define {
                                text "Form"
                                children widget {
                                    SWTLabel.label(SWT.NORMAL,"Form Label")
                                }
                            }
                        }
                    }
                } >> widget {
                    SWTTab.tab(SWT.NORMAL,"Group").define {
                        children widget {
                            new SWTGroup(SWT.FLAT).define {
                                text "GroupTest"
                                children widget {
                                    SWTButton.button(SWT.FLAT).define {
                                        text "Grouped Button"
                                    }
                                }
                            }
                        }
                    }
                } >> widget {
                    SWTTab.tab(SWT.HORIZONTAL,"Sash").define {
                        children new SWTSashForm(SWT.HORIZONTAL).define {
                            spacing 2
                            percentage 1,2
                            children widget {
                                SWTButton.button(SWT.FLAT).define {
                                    text "Test A"
                                }
                            } >> widget {
                                SWTButton.button(SWT.FLAT).define {
                                    text "Test B"
                                }
                            }
                        }
                    }
                }
            }
        } >> widget {
            new SWTCTabPane(SWT.BORDER).define {
                layout SWTGridLayout.cell().define {
                    colSpan 6
                    rowSpan 4
                    fillHeight true
                    fillWidth true
                    columnAlignment SWT.FILL
                    rowAlignment SWT.FILL
                    width 120
                    height 120
                }
                children widget {
                    new SWTCTab(SWT.NORMAL).define {
                        text "CTabTest"
                        children widget {
                            SWTButton.button(SWT.FLAT).define {
                                text "Test Content"
                            }
                        }
                    }
                } >> widget {
                    new SWTCTab(SWT.NORMAL).define {
                        text "ViewForm"
                        children widget {
                            new SWTViewForm(SWT.NORMAL).define {
                                left SWTButton.button(SWT.FLAT).define{
                                    text "TestLeft"
                                }
                                right SWTButton.button(SWT.FLAT).define {
                                    text "TestRight"
                                }
                                center SWTButton.button(SWT.FLAT).define {
                                    text "TestCenter"
                                }
                                bottom SWTButton.button(SWT.FLAT).define {
                                    text "TestBottom"
                                }
                            }
                        }
                    }
                } >> widget {
                    new SWTCTab(SWT.NORMAL).define {
                        text "cbanner"
                        children widget {
                            new SWTCBanner(SWT.FLAT).define {
                                left SWTButton.button(SWT.FLAT).define {
                                    text "TestLeft"
                                }
                                right SWTButton.button(SWT.FLAT).define {
                                    text "TestRight"
                                    size 420,SWT.DEFAULT
                                }
                                bottom SWTButton.button(SWT.FLAT).define {
                                    text "TestBottom"
                                }
                            }
                        }
                    }
                } >> widget{
                    new SWTCTab(SWT.NORMAL).define {
                        text "expandable"
                        children widget {
                            new SWTExpandBar(SWT.FLAT).define {
                                children widget {
                                    new SWTExpandItem(SWT.DEFAULT).define {
                                        text "expand demo"
                                        size SWT.DEFAULT,80
                                        children SWTPane.pane(SWT.NORMAL).define {
                                            layout SWTRowLayout.rowLayout(SWT.VERTICAL)
                                            children widget {
                                                SWTButton.button(SWT.FLAT).define {
                                                    text "Text Expand"
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } >> widget {
                    new SWTCTab(SWT.NORMAL).define {
                        text "formLayout"
                        children widget {
                            SWTPane.pane(SWT.FLAT).define {
                                layout new SWTFormLayout().define {

                                }
                                children widget {
                                    SWTLabel.label(SWT.FLAT,"Test Label").define {
                                        id "tlb"
                                    }
                                } >> widget {
                                    SWTButton.button(SWT.FLAT).define {
                                        text "Test Form"
                                        layout new SWTFormData().define {
                                            right().id("tlb")
                                                    .offset(400)
                                            top().id("tlb")
                                                    .offset(80)
                                        }
                                    }
                                } >> widget {
                                    SWTComboBox.comboBox(SWT.FLAT).define {
                                        layout new SWTFormData().define {
                                            right().id("tlb")
                                                    .offset(200)
                                            top().id("tlb")
                                                    .offset(80)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
