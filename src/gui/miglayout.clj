(ns gui.miglayout
(:require gui.coremig)
(:import 
  (net.miginfocom.swt MigLayout)
  (org.eclipse.swt SWT)
  (org.eclipse.swt.widgets Control TabItem )
  (swt.widgets Label Text Button SashForm Composite StyledText)
  ))

(defn bit-ors [& args] (reduce bit-or args))

(defn swt-dbuff [& args] 
  (reduce bit-or args))
  
(defn get-composite [obj] 
 (if (instance? Control obj)
   (cast org.eclipse.swt.widgets.Composite obj)
   (cast org.eclipse.swt.widgets.Composite (.getControl (cast TabItem obj)))))

(defn create-label 
  ([parent text layout style]
    (doto
      (Label. (get-composite parent) (swt-dbuff style) layout)
      (.setText text)))
  ([parent text layout] (create-label parent text layout SWT/LEFT)))
  
(defn create-text-field 
  ([parent text layout style]
    (doto 
      (Text. (get-composite parent) (swt-dbuff style SWT/BORDER) layout)
      (.setText text)))
  ([parent text layout] (create-text-field parent text layout 
     SWT/SINGLE)))
     
(defn create-styled-text
  ([parent text layout style]
    (doto 
     (StyledText. (get-composite parent) (swt-dbuff style SWT/BORDER) layout)
     (.setText text)))
  ([parent text layout] (create-styled-text parent text layout 
     SWT/NONE)))  
 
(defn create-button 
  ([parent text layout style]
    (doto 
      (Button. (get-composite parent) (swt-dbuff style) layout)
      (.setText text)))
  ([parent text layout] (create-button parent text layout 
                          (bit-or SWT/PUSH SWT/NO_BACKGROUND))))
                           
                          
  ;;;;;;;;   
