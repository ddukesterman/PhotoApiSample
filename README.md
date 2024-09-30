# PhotoApiSample

Sample project that uses Android Compose UI, Kotlin, Coroutines, Retrofit, and Hilt. It can access both image APIs, Pixels and Pixabay.

You must update the app's build.gradle to use your own API keys on line 24 and 25. You must keep the slash and double quotes.

```
        buildConfigField("String", "PIXABAY_API_KEY", "\"KEY_GOES_HERE\"")
        buildConfigField("String", "PEXEL_API_KEY", "\"KEY_GOES_HERE\"")
```

## Multiple API switch
You can go into the settings screen from the menu to switch between image providers.

