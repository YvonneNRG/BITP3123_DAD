
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JSONWebServiceClient {

	private JFrame frame;
	
	private JPanel centerPane;
	private JPanel eastPane;
	
	private JButton btnReadJson;
	private JButton btnGetFromPHP;
	
	private JTextField textField1;
	private JTextField textField2;
	private JTextField txtFldName;
	private JTextField txtFldAge;
	private JTextField txtFldProgram;
	private JTextField txtFldFromPHP;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JSONWebServiceClient window = new JSONWebServiceClient();
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
	public JSONWebServiceClient() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		Dimension dButton = new Dimension(100,30);
		Dimension dTextField = new Dimension(170,30);
		Dimension dLabelField = new Dimension(100,30);
		
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		textField1 = new JTextField();
		textField1.setPreferredSize(dTextField);

		textField2 = new JTextField();
		textField2.setPreferredSize(dTextField);

		txtFldName = new JTextField();
		txtFldName.setPreferredSize(dTextField);
		
		txtFldAge = new JTextField();
		txtFldAge.setPreferredSize(dTextField);
		
		txtFldProgram = new JTextField();
		txtFldProgram.setPreferredSize(dTextField);

		txtFldFromPHP = new JTextField();
		txtFldFromPHP.setPreferredSize(dTextField);
		
		btnReadJson = new JButton("Read JSON");
		btnReadJson.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        JSONObject jobjFile;
				try {
					jobjFile = new JSONObject(new JSONTokener(new FileReader("C:/DataJSON.json")));
					
					String strVar1 = jobjFile.getString("sayHello");
					textField1.setText(strVar1);
					
					double dblPrice1 = jobjFile.getDouble("dblPrice1");
					int intQty = jobjFile.getInt("intQty");
					textField2.setText(String.valueOf(dblPrice1 * intQty));
					
					JSONArray jsnArr = jobjFile.getJSONArray("arrStudent");
					for(int i = 0; i < jsnArr.length(); i++) {
						JSONObject jsnStud = jsnArr.getJSONObject(i);
						if(jsnStud.getString("studName").equalsIgnoreCase("Najib")) {
							txtFldName.setText(jsnStud.getString("studName"));
							txtFldAge.setText(jsnStud.getString("age"));
							txtFldProgram.setText(jsnStud.getString("course"));
						}
					}
					
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		        
		    }
		});
		
		btnGetFromPHP = new JButton("Get From PHP");
		btnGetFromPHP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
			
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				String strUrl = "http://localhost/webServiceJSON/helloJSON.php";
				JSONObject jsonObj = makeHttpRequest(strUrl, "POST", params);
				String strFromPHP;
				
				try {
					
					strFromPHP = jsonObj.getString("message");
					txtFldFromPHP.setText(strFromPHP);
					
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
				
		});
		
			// West panel
			JPanel westPane = new JPanel();
			westPane.setPreferredSize(new Dimension(100, 300));
			
				JLabel jsonFileLbl = new JLabel("JSON File: ");
				jsonFileLbl.setPreferredSize(dLabelField);
				jsonFileLbl.setHorizontalAlignment(4);
				
				JLabel emptyLbl = new JLabel("");
				emptyLbl.setPreferredSize(dLabelField);
				
				JLabel studNameLbl = new JLabel("Stud.Name: ");
				studNameLbl.setPreferredSize(dLabelField);
				studNameLbl.setHorizontalAlignment(4);
				//studNameLbl.setBorder(BorderFactory.createEmptyBorder(70, 0, 0, 0));
				
				JLabel studAgeLbl = new JLabel("Stud.Age: ");
				studAgeLbl.setPreferredSize(dLabelField);
				studAgeLbl.setHorizontalAlignment(4);
				
				JLabel studProgramLbl = new JLabel("Stud.Program: ");
				studProgramLbl.setPreferredSize(dLabelField);
				studProgramLbl.setHorizontalAlignment(4);
				
				JLabel fromPHPLbl = new JLabel("From PHP: ");
				fromPHPLbl.setPreferredSize(dLabelField);
				fromPHPLbl.setHorizontalAlignment(4);
				
			westPane.add(jsonFileLbl);
			westPane.add(emptyLbl);
			westPane.add(studNameLbl);
			westPane.add(studAgeLbl);
			westPane.add(studProgramLbl);
			westPane.add(fromPHPLbl);
		
		frame.setVisible(true);
		
			// East panel
			eastPane = new JPanel(new BorderLayout());
			eastPane.setBounds(20, 20, 100, 180);
			
				// Read JSON button
				btnReadJson.setPreferredSize(dButton);
			eastPane.add(btnReadJson,BorderLayout.NORTH);
			
				// Get From PHP button
				btnGetFromPHP.setPreferredSize(dButton);
			eastPane.add(btnGetFromPHP,BorderLayout.SOUTH);
		
			// Center panel
			centerPane = new JPanel();
			centerPane.setBounds(20, 20, 200, 300);
			
			centerPane.add(textField1);
			centerPane.add(textField2);
			centerPane.add(txtFldName);
			centerPane.add(txtFldAge);
			centerPane.add(txtFldProgram);
			centerPane.add(txtFldFromPHP);
			
			// Empty south panel
			JPanel southPane = new JPanel();
			southPane.setPreferredSize(new Dimension(450,60));
		
		// Add panel to frame
		frame.add(centerPane, BorderLayout.CENTER);
		frame.add(westPane,BorderLayout.WEST);
		frame.add(eastPane, BorderLayout.EAST);
		frame.add(southPane,BorderLayout.SOUTH);
	}


	private JSONObject makeHttpRequest(String url, String method, List<NameValuePair> params) {
		
		InputStream is = null;
		String json = "";
		JSONObject jObj = null;
		//Making HTTP Request
		
		try {
			
			//check for request method
			if(method == "POST") {
				
				// Request method is POST
				// defaultHttpClient
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(url);
				httpPost.setEntity(new UrlEncodedFormEntity(params));
				
				HttpResponse httpResponse = httpClient.execute(httpPost);
				HttpEntity httpEntity = httpResponse.getEntity();
				is = httpEntity.getContent();
				
			}else if(method == "GET") {
				
				// Request method is GET
				DefaultHttpClient httpClient = new DefaultHttpClient();
				String paramString = URLEncodedUtils.format(params, "utf-8");
				url += "?" + paramString;
				HttpGet httpGet = new HttpGet(url);
				
				HttpResponse httpResponse = httpClient.execute(httpGet);
				HttpEntity httpEntity = httpResponse.getEntity();
				is = httpEntity.getContent();
			}
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"),8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while((line = reader.readLine()) != null) {
				sb.append(line + "\n|");
			}
			is.close();
			json = sb.toString();
			jObj = new JSONObject(json);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// Return JSON String
		return jObj;
	}
}