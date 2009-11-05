(ns ecore.utils.xmlScratch
  (:use ecore.utils.pluginXML))
  
(defmacro view [& args]
 `[:view ~(hash-map-list args)])
 
(defmacro eview [& args]
 `(extension "org.eclipse.ui.views"
      (view ~@args)))

(defmacro eperspective [& args]
  `(extension "org.eclipse.ui.perspectiveExtensions"
     [:perspectiveExtension {:targetID  "*"}
       ~@args]))

(with-xml 2
	(eview :class "de.vogella.rcp.intro.view.MyView"
         :id "de.vogella.rcp.intro.view.MyView"
         :name "name"
         :restorable "true"   )
   (eperspective 
     (view   
        :id "de.vogella.rcp.intro.view.MyView"
        :minimized "false"
        :ratio ".95f"
        :relationship "stack"
        :relative "org.eclipse.ui.editorss"
        :visible "true")))   
(comment

   <extension
         point="org.eclipse.ui.views">
      <view
            class="de.vogella.rcp.intro.view.MyView"
            id="de.vogella.rcp.intro.view.MyView"
            name="name"
            restorable="true">
      </view>
   </extension>
   <extension
         point="org.eclipse.ui.perspectiveExtensions">
      <perspectiveExtension
            targetID="*">
         <view
               id="de.vogella.rcp.intro.view.MyView"
               minimized="false"
               ratio=".95f"
               relationship="stack"
               relative="org.eclipse.ui.editorss"
               visible="true">
         </view>
      </perspectiveExtension>
   </extension>
)