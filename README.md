# Introduction

TunaSushi is a set of view libraries that help developers follow best practices, reduce boilerplate code, and write code that works consistently on Android versions and devices.

# Demo Example

![WechatIMG1330](https://user-images.githubusercontent.com/8152969/123501852-60018a80-d67a-11eb-908b-b2b81e9e5667.jpeg)![WechatIMG1331](https://user-images.githubusercontent.com/8152969/123501857-714a9700-d67a-11eb-8ede-f13b61dd130c.jpeg)
![WechatIMG1332](https://user-images.githubusercontent.com/8152969/123501860-74458780-d67a-11eb-9ff3-db0385a1bec8.jpeg)
![Uploading WechatIMG1333.jpeg…]()
![WechatIMG1334](https://user-images.githubusercontent.com/8152969/123501864-76a7e180-d67a-11eb-808a-91e040d23d0b.jpeg)
![WechatIMG1335](https://user-images.githubusercontent.com/8152969/123501865-77d90e80-d67a-11eb-8106-bc19dca81c79.jpeg)
![WechatIMG1336](https://user-images.githubusercontent.com/8152969/123501866-79a2d200-d67a-11eb-9254-de49006438a6.jpeg)

# How To Use

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
	
# Extra Attention

If the attributes defined in the TunaSushi library and the attributes defined in other libraries have the same name and different types, compilation errors will occur.The solution is to define an attribute with the same name in the attr of the main project, but the type contains both.

For example, The content attribute in TView in the TunaSushi library is string, and the content attribute in the constraintlayout library is reference.

When the types of attributes with the same name are inconsistent, you can configure a single content in the project, and the attribute is the union of the two.such as below.

	<attr name="content" format="reference|string" />
