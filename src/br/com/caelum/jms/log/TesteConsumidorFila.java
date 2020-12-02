package br.com.caelum.jms.log;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

public class TesteConsumidorFila {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {

		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");

		Connection connection = factory.createConnection();
		connection.start();
		// abstrai o trabalho transacional e confirmação do recebimento da mensagem.
		// o primeiro parâmetro(false) define se queremos usar o tratamento da transação
		// como explícito.
		// segundo parametro serve para confirmar automaticamente o recebimento da
		// messagem JMS.
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		// organizar o recebimento e a entrega das mensagens
		Destination fila = (Destination) context.lookup("LOG");
		MessageConsumer consumer = session.createConsumer(fila);

		consumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {
				TextMessage textMessage = (TextMessage) message;
				try {
					System.out.println(textMessage.getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}

		});
		
		new Scanner(System.in).nextLine();

		session.close();
		connection.close();
		context.close();

	}

}
