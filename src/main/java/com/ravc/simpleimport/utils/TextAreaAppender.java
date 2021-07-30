package com.ravc.simpleimport.utils;

import javax.swing.JTextArea;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Layout;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;

public class TextAreaAppender extends AppenderSkeleton {

	private static final String DEFAULT_PATTERN = "%d{ABSOLUTE} %5p %c{1}:%L - %m%n";
	private JTextArea jTextArea;

	public TextAreaAppender(JTextArea jTextArea) {
		if (jTextArea == null) {
			throw new IllegalArgumentException("jTextArea n�o pode ser nulo.");
		}

		this.jTextArea = jTextArea;
	}

        @Override
	public void close() {
		// Nada a fazer nesse m�todo
	}

        @Override
	public boolean requiresLayout() {
		return true;
	}

	/*
	 * Optei por uma implementa��o onde o layout � opcional, se ningu�m informar
	 * um layout, � criado um layout default.
	 */
	@Override
	public Layout getLayout() {
		if (layout == null) {
			layout = new PatternLayout(DEFAULT_PATTERN);
		}

		return super.getLayout();
	}

	@Override
	protected void append(LoggingEvent event) {
		String formatted = getLayout().format(event);

		jTextArea.append(formatted);

		// Se a mensagem possu� informa��es sobre a exce��o que causou o erro
		if (event.getThrowableInformation() != null) {
			jTextArea.append(ExceptionUtils.getStackTrace(event
					.getThrowableInformation().getThrowable()));
		}
	}

}