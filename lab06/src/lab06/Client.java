package lab06;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Client {

	private JFrame frame;

	private JLabel labelID;
	private JLabel labelName;

	private JTextField textFieldID;
	private JTextField textFieldName;

	private JButton btnSearch;

	private JTextArea txtArea1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client window = new Client();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Client() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Dimension dTextField = new Dimension(80, 20);
		Dimension dButton = new Dimension(80, 20);

		textFieldID = new JTextField();
		textFieldID.setBounds(10, 5, 75, 20);
		textFieldID.setPreferredSize(dTextField);

		textFieldName = new JTextField();
		textFieldName.setBounds(10, 33, 75, 20);
		textFieldName.setPreferredSize(dTextField);

		txtArea1 = new JTextArea();
		txtArea1.setPreferredSize(new Dimension(400, 200));

		// West pane
		JPanel westPane = new JPanel();
		westPane.setPreferredSize(new Dimension(80, 80));

		labelID = new JLabel("Stud Login ID");
		labelID.setPreferredSize(dTextField);
		labelID.setHorizontalAlignment(4);

		labelName = new JLabel("Stud Name");
		labelName.setPreferredSize(dTextField);
		labelName.setHorizontalAlignment(4);

		westPane.add(labelID);
		westPane.add(labelName);

		// Center panel
		JPanel centerPane = new JPanel();
		centerPane.setBounds(20, 20, 100, 100);
		centerPane.setLayout(null);

		centerPane.add(textFieldID);
		centerPane.add(textFieldName);

		// South panel
		JPanel southPane = new JPanel();
		southPane.setPreferredSize(new Dimension(450, 200));
		southPane.add(txtArea1);

		frame.setVisible(true);
		frame.getContentPane().add(centerPane, BorderLayout.CENTER);

		btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("selectFn", "searchStudent"));
				params.add(new BasicNameValuePair("studId", textFieldID.getText()));
				params.add(new BasicNameValuePair("studName", textFieldName.getText()));

				String strUrl = "http://localhost/webServiceJSON/genericWebService.php";
				// String strUrl = "http://10.131.77.174/webServiceJSON/genericWebService.php";
				// JSONObject jsonObj = makeHttpRequest(strUrl, "POST", params);
				JSONArray jsonArray = makeHttpRequest(strUrl, "POST", params);

		        StringBuilder strSetText = new StringBuilder();

		        try {
		        	
		            // If the response contains an error message
		        	if (jsonArray.length() == 1 && jsonArray.getJSONObject(0).has("message")) {
		                String errorMessage = jsonArray.getJSONObject(0).getString("message");
		                final String finalErrorMessage = errorMessage; // Create a final variable to use in the Runnable

		                SwingUtilities.invokeLater(new Runnable() {
		                    public void run() {
		                        txtArea1.setText(finalErrorMessage);
		                    }
		                });
		            }
		        	
		        	else {
			            for (int i = 0; i < jsonArray.length(); i++) {
			                JSONObject record = jsonArray.getJSONObject(i);
			                String studFirstName = record.getString("firstname");
			                String studLastName = record.getString("lastname");
			                String studLogin = record.getString("login");
			                String studLastLog = record.getString("last_login_on");
	
			                strSetText.append("First Name: ").append(studFirstName)
			                        .append(" || Last Name: ").append(studLastName)
			                        .append(" || Login ID: ").append(studLogin)
			                        .append(" || Last Login: ").append(studLastLog)
			                        .append("\n");
			            }
		        	}
		        	
		            txtArea1.setText(strSetText.toString());
		        } catch (JSONException e1) {
		            e1.printStackTrace();
		        }

			}

		});

		// East panel
		JPanel eastPane = new JPanel(new BorderLayout());
		centerPane.add(eastPane);
		eastPane.setBounds(168, 0, 75, 80);

		// Get From PHP button
		btnSearch.setPreferredSize(dButton);
		eastPane.add(btnSearch, BorderLayout.NORTH);
		frame.getContentPane().add(westPane, BorderLayout.WEST);
		frame.getContentPane().add(southPane, BorderLayout.SOUTH);
	}

	private JSONArray makeHttpRequest(String url, String method, List<NameValuePair> params) {
	    InputStream is = null;
	    String json = "";
	    JSONArray jsonArray = null;

	    try {
	    	
	        // check for request method
	        if (method == "POST") {
	        	
	            // Request method is POST
	            DefaultHttpClient httpClient = new DefaultHttpClient();
	            HttpPost httpPost = new HttpPost(url);
	            httpPost.setEntity(new UrlEncodedFormEntity(params));

	            HttpResponse httpResponse = httpClient.execute(httpPost);
	            HttpEntity httpEntity = httpResponse.getEntity();
	            is = httpEntity.getContent();
	        } else if (method == "GET") {
	        	
	            // Request method is GET
	            DefaultHttpClient httpClient = new DefaultHttpClient();
	            String paramString = URLEncodedUtils.format(params, "utf-8");
	            url += "?" + paramString;
	            HttpGet httpGet = new HttpGet(url);

	            HttpResponse httpResponse = httpClient.execute(httpGet);
	            HttpEntity httpEntity = httpResponse.getEntity();
	            is = httpEntity.getContent();
	        }

	        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
	        StringBuilder sb = new StringBuilder();
	        String line = null;

	        while ((line = reader.readLine()) != null) {
	            sb.append(line + "\n|");
	        }
	        
	        is.close();
	        json = sb.toString();

	        // Try to parse the response as a JSONArray
	        jsonArray = new JSONArray(json);
	        
	    } catch (JSONException e) {
	    	
	        // If it's not a JSONArray, wrap the response in a JSONArray
	        try {
	        	
	            JSONObject jsonObj = new JSONObject(json);
	            jsonArray = new JSONArray();
	            jsonArray.put(jsonObj);
	            
	        } catch (JSONException e1) {
	            e1.printStackTrace();
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return jsonArray;
	}
}
