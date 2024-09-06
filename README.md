# Weather Telegram Bot

This project is a Telegram bot that provides weather information for London using the WeatherAPI.

## Features

- Responds to user messages with current weather information for London
- Handles errors gracefully and informs users of any issues

## Setup

1. Clone the repository:
   ```
   git clone https://github.com/yourusername/weather-telegram-bot.git
   cd weather-telegram-bot
   ```

2. Set up your API keys:
   - Replace `API_KEY` in `JsonParser.java` with your WeatherAPI key
   - Replace `API_KEY` in `TelegramAPI.java` with your Telegram Bot token

3. Build the project:
   ```
   mvn clean install
   ```

## Running the Bot

Run the main class `TelegramAPI.Main`:
