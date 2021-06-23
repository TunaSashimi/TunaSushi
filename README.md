# TunaSushi

TunaSushi is a set of view libraries that help developers follow best practices, reduce boilerplate code, and write code that works consistently on Android versions and devices.

Step 1. Add the JitPack repository to your build file

To get a Git project into your build:
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
Step 2. Add the dependency
  
  	dependencies {
	        implementation 'com.github.TunaSashimi:TunaSushi:1.0.01'
	}
	
Attention

If the attributes defined in the TunaSushi library and the attributes defined in other libraries have the same name and different types, compilation errors will occur.The solution is to define an attribute with the same name in the attr of the main project, but the type contains both.

For example, The content attribute in TView in the TunaSushi library is string, and the content attribute in the constraintlayout library is reference.

When the types of attributes with the same name are inconsistent, you can configure a single content in the project, and the attribute is the union of the two.such as below.

	<attr name="content" format="reference|string" />
