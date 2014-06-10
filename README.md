Android-ParallaxHeaderViewPager
===============================

This library is pager UI for Android.<br>
(such as pager UI in Play NewsStand Application)

to use this project you need to add :
 1. NineOldAndroids (https://github.com/JakeWharton/NineOldAndroids) <br>
 2. Appcompat (http://developer.android.com/tools/support-library/features.html#v7-appcompat) <br>
![screens](screen.png)


I made this based on very good libraries such as below.<br>
 1. NotBoringActionBar (https://github.com/flavienlaurent/NotBoringActionBar) <br>
 2. PagerSlidingTabStrip (https://github.com/astuetz/PagerSlidingTabStrip) <br>


How to implement? see below.<br>
1. Change ListView in NotBoringActionBar to ViewPager.<br>
2. Make ListView or ScrollView of Fragment in ViewPager.<br>
3. Make Interface that Activity can get a Listener when you use OnScroll() in ListView or ScrollView.<br>
4. Make Interface which is available to scroll when some events is happened in ListView or ScrollView.<br>

From now on, you can fix scrollY value in ListView wherever the pages are swiped through the interface you've just made.<br>


# Google Play Store Download

https://play.google.com/store/apps/details?id=com.kmshack.newsstand


# Video

http://www.youtube.com/watch?v=sCP-b0a1x5Y



# Developed By

 * Kim Min Soo - <kmshack@naver.com>
 * http://www.kmshack.kr



[![Analytics](https://ga-beacon.appspot.com/UA-51734472-1/Android-ParallaxHeaderViewPager/readme)](https://github.com/kmshack/Android-ParallaxHeaderViewPager)



# License

    Copyright 2014 Kim Min Soo

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
    
