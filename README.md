# mattermost-java-bindings
A project that aims at providing java bindings to the mattermost REST API.

It's fairly straight forward atm, only providing what we need in my company (web hooks).

## Example

There's a full example in com.dsuepke.mattermost.MattermostExample. Basically:

```java
public class MattermostExample {

	private static final String URL_BASE = "https://myUrl/hooks/";
	private static final String HOOK_TEST_CHANNEL = "myhook";

	private static final String BOT1 = "MyBot";


	public static void main(String[] args) throws IOException {
		MattermostWebHook hook = new MattermostWebHook(URL_BASE + HOOK_TEST_CHANNEL);
		hook.sendMessage("Hello World!", BOT1);
		hook.sendMessage("Foo bar!", BOT1);
	}

}
```
