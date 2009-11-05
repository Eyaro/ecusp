(ns gui.repl
 (:use utils.sequence
    gui.miglayout)
 (:gen-class
   :init init
   :methods [[createControls [org.eclipse.swt.widgets.Composite] void]])
 (:import
    (net.miginfocom.swt MigLayout)
  (org.eclipse.swt.layout FormAttachment FormData FormLayout) 
  (org.eclipse.swt SWT)
	;(org.eclipse.swt.widgets Listener)
	;(org.eclipse.swt.widgets Text)
	(swt.widgets SashForm)
	)) 
   
(import '(org.eclipse.swt.layout FillLayout))
     		  
(defn -init [] [[][]])
(defn -createControls [this parent]
  (.setLayout parent (FillLayout.))
  (let [sash (SashForm. parent SWT/VERTICAL)]
   (create-styled-text sash "" "" (bit-ors SWT/MULTI SWT/H_SCROLL SWT/V_SCROLL SWT/READ_ONLY))
   (create-styled-text sash "" "" (bit-ors SWT/MULTI SWT/H_SCROLL SWT/V_SCROLL))
   ;(create-sash parent)
  ))
   
   
   