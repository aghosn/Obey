Obey
====

Obey is  a linter tool for Scala. Obey enables to define rules, based on scala.meta trees and the TQL library, corresponding to code health requirements. A rule can generate warnings and/or propose to correct the source code automatically. 

In order to use the Obey as a compiler plugin, that runs a phase called 'obey', use the following line: 
~~~
addCompilerPlugin("com.github.aghosn" % "plugin_2.11.5" % "0.1.0­SNAPSHOT")
~~~
The compiler plugin defines the following options: 
* all:<OFL> enables to apply a filter on the entire set of rules considered
* warn:<OFL> enables to select the rules that will only generate warnings
* fix:<OFL> enables to select the rules that will automatically correct the source code
* addRules:<path to compiled classes> enables to add user-defined rules to a project

The option filtering language (OFL) correponds to the following parser: 
~~~
tag := “[\w\*]+”.r
tags := “{” ~ tag ~ (“[;,]”.r ~ tag).* ~ “}”
OFL := ("[+-]".r ~ tags).*
~~~

For example the following expression 
~~~
+{List*, Set} - {Array}
~~~ 
selects all the rules that have a tag/name begining by List or equal to Set, while excluding all the ones that have a tag/name equal to Array

In order to use Obey as an SBT autoplugin, use the following line: 
~~~
addSbtPlugin("com.github.aghosn" %% "sbt­obeyplugin" % "0.1­SNAPSHOT")
~~~

and 
~~~
lazy val myProject = Project(...) enablePlugins(obeyplugin)
~~~
 For an example of how to use Obey, refer to the IObey project or the demo-obey one on my github. 
