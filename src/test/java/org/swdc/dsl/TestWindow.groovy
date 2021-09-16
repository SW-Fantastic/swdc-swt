import org.eclipse.jface.dialogs.MessageDialog
import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.Display
import org.eclipse.ui.forms.widgets.Section
import org.swdc.dsl.TestCell
import org.swdc.dsl.TestController
import org.swdc.swt.layouts.SWTBorderLayout
import org.swdc.swt.layouts.SWTGridLayout
import org.swdc.swt.layouts.SWTRowLayout
import org.swdc.swt.widgets.SWTButton
import org.swdc.swt.widgets.SWTComboBox
import org.swdc.swt.widgets.SWTDateTime
import org.swdc.swt.widgets.SWTLabel
import org.swdc.swt.widgets.SWTLink
import org.swdc.swt.widgets.SWTList
import org.swdc.swt.widgets.SWTTree
import org.swdc.swt.widgets.SWTWidgets
import org.swdc.swt.widgets.form.SWTFormExpandPane
import org.swdc.swt.widgets.form.SWTFormHyperLink
import org.swdc.swt.widgets.form.SWTFormText
import org.swdc.swt.widgets.pane.SWTPane
import org.swdc.swt.widgets.SWTScale
import org.swdc.swt.widgets.SWTSlider
import org.swdc.swt.widgets.SWTSpinner
import org.swdc.swt.widgets.pane.SWTScrollPane
import org.swdc.swt.widgets.pane.SWTTab
import org.swdc.swt.widgets.pane.SWTTabPane
import org.swdc.swt.widgets.SWTTable
import org.swdc.swt.widgets.SWTTableColumn
import org.swdc.swt.widgets.SWTText
import org.swdc.swt.widgets.Stage
import org.swdc.swt.widgets.form.SWTFormSection

class TestWindow extends Stage {

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
                SWTTableColumn.tableColumn(SWT.CANCEL, "Test Col").define {
                    width 120
                    factory (TestCell obj) -> obj.getName()
                }
            } >> widget {
                SWTTableColumn.tableColumn(SWT.CANCEL, "Col").define {
                    width 120
                    factory (TestCell obj) -> obj.getProp()
                }
            }
        }
    }

    void create() {

        controller new TestController()
        text "hello"
        size 800,600
        layout SWTGridLayout.gridLayout().define {
            margin 6,6
            spacing 8,8
            columns 6
        }
        children widget {
            SWTLabel.label(SWT.NORMAL,"Test Label").define {
                text "change"
                color "#66CCFF"
                backgroundColor "#E6E6E6"
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
            SWTButton.button(SWT.NONE, "Test Button").define {
                action "hello"
            }
        } >> widget {
            SWTButton.button(SWT.NORMAL, "Test Dialog").define {
                action {
                    MessageDialog.openInformation(this.getShell(),"标题","窗口显示的内容。")
                }
            }
        } >> widget {
            demoTable()
        } >> widget {
            SWTComboBox.comboBox(SWT.NORMAL).define {
                layout SWTGridLayout.cell().define {
                    colSpan 6
                    fillWidth true
                    rowAlignment SWT.FILL
                }
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

                children widget {
                    SWTTab.tab(SWT.NORMAL,"Tab 0").define {
                        children widget {
                            SWTButton.button(SWT.NORMAL,"Test Tab")
                        }
                    }
                } >> widget {
                    SWTTab.tab(SWT.NORMAL,"Tab 1").define {

                        text "Tab 1"

                        children widget {
                            SWTPane.pane(SWT.NORMAL).define {
                                layout SWTBorderLayout.borderLayout()
                                children widget {
                                    SWTButton.button(SWT.NORMAL,"BD 0").define {
                                        layout SWTBorderLayout.top(60)
                                    }
                                } >> widget {
                                    SWTButton.button(SWT.NORMAL, "BD 1").define {
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
                                            SWTButton.button(SWT.NORMAL,"Frm Button").define {
                                                action {
                                                    MessageDialog.openInformation(this.getShell(),"Frm Button","clicked")
                                                }
                                            }
                                        } >> widget{
                                            SWTFormHyperLink.hyperLink(SWT.NORMAL).define {
                                                text "Hyper Link"
                                                action {
                                                    MessageDialog.openInformation(this.getShell(),"HyperLink","HyperLink 被激活。。")
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
                }
            }
        }
    }

    static void main(String[] args) {
        Display display = SWTWidgets.getDisplay();

        TestWindow window = new TestWindow()
        window.create()
        window.show()

        while(!window.isDisposed()){
            if(!display.readAndDispatch()){
                display.sleep();
            }
        }
        display.dispose();

    }


}
