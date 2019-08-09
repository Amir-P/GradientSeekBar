# GradientSeekBar
Beautiful gradient SeekBar implementation in Android for API 17+

![sample](https://github.com/Amir-P/GradientSeekBar/blob/master/Screenshot.png)

# Install
**Gradle**
```
dependencies {
  implementation 'com.amir-p:GradientSeekBar:1.0.0'
}
```

**Maven**
```xml
<dependency>
  <groupId>com.amir-p</groupId>
  <artifactId>GradientSeekBar</artifactId>
  <version>1.0.0</version>
  <type>pom</type>
</dependency>
```

# Usage
```xml
<com.amir_p.GradientSeekBar.GradientSeekBar
    android:layout_width="300dp"
    android:layout_height="150dp"
    android:layout_centerInParent="true"
    app:progressEndColor="#01579B"
    app:progressStartColor="#311B92"
    app:title="GradientSeekBar"
    app:titleTextSize="32sp"
    app:value="1" />
```
# Style
```xml
<style name="GradientSeekBarStyle">
    <item name="progressEndColor">#01579B</item>
    <item name="progressStartColor">#311B92</item>
    <item name="titleTextSize">32dp</item>
    <item name="titleGravity">center</item>
    <item name="cornerRadius">32dp</item>
</style>
```
