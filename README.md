# Dicoding Event App

## Objective
The *Dicoding Event App* is a mobile application designed to help users discover and manage events. The app includes features such as adding events to favorites, managing themes, and displaying detailed information about events. This project demonstrates the integration of various Android components and architecture, including data persistence and network operations.

### Skills Learned

- Implementation of a **Favorite Feature** with a database to allow users to add and remove events from their favorites.
- Creation of a dedicated screen to display a list of favorite events.
- Development of a detailed view for each favorite event.
- Implementation of theme settings, allowing users to switch between light and dark themes using key-value storage.
- Ensuring the selected theme persists across app restarts by observing data changes.
- Ensuring all UI components remain visible and functional regardless of the selected theme.
- Maintenance of existing features and components from the initial submission, including multiple event lists and a bottom navigation system.
- Use of **Loading Indicators** during data fetch operations.
- Integration of **Daily Reminder** settings using WorkManager for notifications about upcoming events.
- Proper implementation of **Repository Pattern** and Dependency Injection for data management.
- Use of **Coroutines** for asynchronous tasks with Retrofit and Room.
- Error handling to display messages when data cannot be retrieved.
- Code quality improvement, with minimal warnings during inspection.
- Displaying two types of event lists using bottom navigation, categorizing active/upcoming and completed events.

## Features

1. **Favorite Feature:** Users can add or remove events from their favorites and view a dedicated list of favorite events.
2. **Event Detail View:** Displays detailed information about each event, including:
   - Image (logo or media cover)
   - Event name
   - Organizer name
   - Event time
   - Remaining quota
   - Event description
   - Link to the event
3. **Theme Management:** Users can switch between light and dark themes. The selected theme persists even after closing and reopening the app, ensuring that all components remain visible and clear.
4. **Loading Indicators:** Visual indicators inform users when data is being loaded from the API.
5. **Daily Reminder Notifications:** Users receive notifications for upcoming events, configurable through the app's settings.
6. **Repository and Dependency Injection:** Data fetched from the API is managed through a well-structured repository pattern.
7. **Coroutines for Asynchronous Tasks:** Used to manage network requests and database operations efficiently.
8. **Error Handling:** Provides user feedback when data retrieval fails, such as during network outages.
9. **Bottom Navigation:** Features two lists of events (active/upcoming and completed) accessible via bottom navigation.

## Optional Features

1. **Home Page:** A new fragment in the bottom navigation displays a horizontal RecyclerView or carousel showcasing up to 5 active and completed events.
2. **Search Functionality:** Users can search for events using a SearchBar or SearchView, utilizing the search endpoint for results.
3. **Android Architecture Component Implementation:** Properly implemented ViewModel to maintain data across configuration changes.
4. **Improved Error Handling:** Enhanced messaging for users when data retrieval fails.
5. **Code Quality Assurance:** Reduced warnings during code inspections to maintain high-quality code standards.

## Screenshots

- **Theme Switch Screen**  
  <img src="https://github.com/Avwaveaf/screenshots/blob/main/de_switch_theme.png" alt="Theme Switch" width="250"/>  
  *This screenshot shows the theme switch functionality, allowing users to toggle between light and dark themes.*

- **Settings Screen**  
  <img src="https://github.com/Avwaveaf/screenshots/blob/main/de_setting_screen.png" alt="Settings" width="250"/>  
  *The settings screen provides options for theme management and daily reminders.*

- **Home Screen**  
  <img src="https://github.com/Avwaveaf/screenshots/blob/main/de_home_screen.png" alt="Home" width="250"/>  
  *The home screen displays active and upcoming events in an organized manner.*

- **Push Notification**  
  <img src="https://github.com/Avwaveaf/screenshots/blob/main/de_push_notification.png" alt="Push Notification" width="250"/>  
  *This screenshot illustrates the push notification feature, alerting users about upcoming events.*

- **Event Detail Screen**  
  <img src="https://github.com/Avwaveaf/screenshots/blob/main/de_event_detail.png" alt="Event Detail" width="250"/>  
  *The event detail screen provides comprehensive information about the selected event, including links to more details.*

## Steps

1. Implement the **Favorite Feature** using a database for persistent storage of favorite events.
2. Create a separate screen for displaying a list of favorite events.
3. Develop a detailed view for favorite events to show essential event information.
4. Integrate theme management functionality, allowing users to switch between light and dark themes.
5. Ensure the selected theme persists across app restarts by implementing data observation.
6. Maintain existing features from the initial submission, including event listings and navigation.
7. Display loading indicators during API data fetches.
8. Add settings for daily reminders using WorkManager for notifications of upcoming events.
9. Implement the repository pattern and dependency injection for better data management.
10. Utilize coroutines for effective network requests and database operations.
11. Enhance error handling to inform users of issues during data retrieval.
12. Improve code quality to ensure minimal warnings during inspections.

