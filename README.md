# PhotoApiSample

Sample project that uses Android Compose UI, Kotlin, Coroutines, Retrofit, and Hilt. It can access both image APIs, Pixels and Pixabay.

You must update the app's build.gradle to use your own API keys on line 24 and 25. You must keep the slash and double quotes.

```
        buildConfigField("String", "PIXABAY_API_KEY", "\"KEY_GOES_HERE\"")
        buildConfigField("String", "PEXEL_API_KEY", "\"KEY_GOES_HERE\"")
```

## Multiple API switch
You can go into the settings screen from the menu to switch between image providers.

## Bookmarked Photos
You can view bookmarked photos by tapping on the heart icon of an image on the image search screen.


## Suggestions
- Clean up UI a little more. Break up composable functions to sub views. See what can be reused in multiple screens.
- Bookmarked photos right now only store the image URL. Needs to be updated to download the image when bookmarked and reference an image filepath on the bookmark photo item.
- Update the recent searches feature to remove the DropdownMenu and create a new screen with a list of recent searches. Recent searches could be stored in Datastore or Room to persist after leaving the screen.
- Additional unit tests. Currently only have a few. 