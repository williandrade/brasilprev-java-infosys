package com.example.heroku.util;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class CustomPhysicalNamingStrategyUtil implements PhysicalNamingStrategy, Serializable {
	private static final long serialVersionUID = 1L;

	public static final CustomPhysicalNamingStrategyUtil INSTANCE = new CustomPhysicalNamingStrategyUtil();

	@Override
	public Identifier toPhysicalCatalogName(Identifier name, JdbcEnvironment context) {
		return capitalize(name);
	}

	@Override
	public Identifier toPhysicalSchemaName(Identifier name, JdbcEnvironment context) {
		return capitalize(name);
	}

	@Override
	public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
		return capitalize(name);
	}

	@Override
	public Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment context) {
		return capitalize(name);
	}

	@Override
	public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
		return capitalize(name);
	}

	private Identifier capitalize(Identifier name) {
		if (name == null)
			return null;
		if (name.isQuoted())
			return name;

		String text = this.snakeador(name.getText());
		return Identifier.toIdentifier(text);
	}

	private String snakeador(String string) {
		final String regex = "(?=[A-Z][a-z])";
		final String subst = "_";
		string = this.firstCharToLowerCase(string);
		
		final Pattern pattern = Pattern.compile(regex);
		final Matcher matcher = pattern.matcher(string);

		final String result = matcher.replaceAll(subst);

		return result.toUpperCase();
	}

	private String firstCharToLowerCase(String str) {
		if (str == null || str.length() == 0)
			return "";

		if (str.length() == 1)
			return str.toLowerCase();

		char[] chArr = str.toCharArray();
		chArr[0] = Character.toLowerCase(chArr[0]);

		return new String(chArr);
	}
}
