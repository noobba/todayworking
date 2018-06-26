package com.lmh.tw;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class TodayWorking extends Thread {
    JFrame jFrame;
    JTextField jTextField;
    JTextArea jTextArea;
    JScrollPane jScrollPane;
    JButton jButtonEnd;
    JButton jButtonStart;
    JTextField jTextCalX;
    JTextField jTextCalY;
    JButton jButtonGetAxis;
    ImageFind imagefind;
    JButton jButtonGetCalIconAxis;
    JTextField jTextCalIconX;
    JTextField jTextCalIconY;
    JButton jButtonIconCheckAxis;
    
    Robot rob;
    List<Map<String, String>> dataList = new ArrayList<Map<String,String>>();
    JLabel jLabelStat;
    JLabel jLabelStatVal;
    JLabel jLabelIconStat;
    JLabel jLabelIconStatVal;
    JLabel jLabelUserAuth;
    JLabel jLabelUserAuthVal;
    JLabel jLabelAxisStart;
    JLabel jLabelCalIconAxisStart;
    JLabel jLabelComma;
    JLabel jLabelAxisEnd;
    JLabel jLabelCalIconComma;
    JLabel jLabelCalIconAxisEnd;
    JLabel jLabelTitle;
    JLabel jLabelDev;
    JLabel jLabelEmpty;
    JLabel jLabelManual;
    Cursor cursor;
    int mouseX, mouseY;
    String propPath;
    boolean imageCheck = false;
    boolean userAuth = false;
    
    TodayWorking() throws Exception {
        jFrame = new JFrame("오늘은 출근킹 Ver 0.9");
        //jFrame.setBounds(10, 10, 420, 500);
        //jTextField = new JTextField(20);
        jTextArea = new JTextArea();
        //jTextArea.setBounds(10, 10, 400, 500); //JTeatArea 크기 및 위치 지정 
        //jTextArea.setEditable(false);
        //jTextArea.setLineWrap(true);
        jScrollPane = new JScrollPane(jTextArea);
        //jScrollPane.setBounds(10, 10, 400, 500);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        String cr = new File(".").getCanonicalPath();
        Image img = toolkit.getImage(cr + File.separator+"img"+File.separator+"cwmaa.png");
        jFrame.setIconImage(img);
        jFrame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File(cr + File.separator+"img"+File.separator+"cwmaa_background.png")))));
        propPath = cr + File.separator+"config"+File.separator+"config.properties";

        jLabelStat = new JLabel("⊙매크로 상태 : ");
        jLabelStatVal = new JLabel("READY");
        jLabelIconStat = new JLabel("⊙달력 이미지 인식 상태 : ");
        jLabelIconStatVal = new JLabel("READY");
        jLabelUserAuth = new JLabel("⊙사용자 인증 상태 : ");
        jLabelUserAuthVal = new JLabel("READY");
        jLabelAxisStart = new JLabel("⊙날짜 좌표   ( X :");
        jLabelCalIconAxisStart = new JLabel("⊙달력 좌표   ( X :");
        jLabelAxisEnd = new JLabel(" ) ");
        jLabelComma = new JLabel(" , Y :");
        jLabelCalIconAxisEnd = new JLabel(" ) ");
        jLabelCalIconComma = new JLabel(" , Y :");
        jLabelTitle = new JLabel("아래 샘플 데이터가 맞다면 START!");
/*        jLabelDev = new JLabel("<html><font color=red>무단 배포를 금지합니다.</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;만든이 : 미카엘</html>");
        jLabelManual = new JLabel("<html></br></br></br>&nbsp;▶ 좌표얻기 사용법<br>&nbsp;&nbsp;&nbsp;&nbsp;"
        		+ "1) 좌표얻기 버튼 클릭<br>&nbsp;&nbsp;&nbsp;&nbsp;"
        		+ "2) 버튼을 떼지 않은 상태에서 원하는 위치로 포인터 이동<br>&nbsp;&nbsp;&nbsp;&nbsp;"
        		+ "3) 클릭한 버튼을 떼면 해당 좌표값 자동입력 완료</html>");*/
        jLabelDev = new JLabel("<html><font color=red>무단 배포를 금지합니다.</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;만든이 : 미카엘</html>");
        jLabelManual = new JLabel("<html></br></br></br>&nbsp;※ 좌표얻기 사용법<br>&nbsp;&nbsp;&nbsp;&nbsp;: 좌표얻기 버튼 클릭&nbsp;▶&nbsp;버튼 떼지 않고 원하는 위치로 포인터 이동 후 떼면 자동입력</html>");

        jLabelEmpty = new JLabel(" ");
        jTextCalX = new JTextField(4);
        jTextCalY = new JTextField(4);
        jTextCalIconX = new JTextField(4);
        jTextCalIconY = new JTextField(4);
        
        jLabelStat.setFont(new Font("맑은 고딕", Font.BOLD, 12));
        jLabelStatVal.setFont(new Font("맑은 고딕", Font.BOLD, 12));
        jLabelStatVal.setForeground(Color.blue);
        jLabelIconStat.setFont(new Font("맑은 고딕", Font.BOLD, 12));
        jLabelIconStatVal.setFont(new Font("맑은 고딕", Font.BOLD, 12));
        jLabelIconStatVal.setForeground(Color.blue);
        this.jLabelUserAuth.setFont(new Font("맑은 고딕", Font.BOLD, 12));
        this.jLabelUserAuthVal.setFont(new Font("맑은 고딕", Font.BOLD, 12));
        this.jLabelUserAuthVal.setForeground(Color.blue);
        jLabelAxisStart.setFont(new Font("맑은 고딕", Font.BOLD, 12));
        jLabelCalIconAxisStart.setFont(new Font("맑은 고딕", Font.BOLD, 12));
        jLabelAxisEnd.setFont(new Font("맑은 고딕", Font.BOLD, 12));
        jLabelComma.setFont(new Font("맑은 고딕", Font.BOLD, 12));
        jLabelCalIconAxisEnd.setFont(new Font("맑은 고딕", Font.BOLD, 12));
        jLabelCalIconComma.setFont(new Font("맑은 고딕", Font.BOLD, 12));
        jLabelTitle.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        jLabelTitle.setForeground(Color.blue);
        jLabelDev.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
        jLabelDev.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelManual.setFont(new Font("맑은 고딕", Font.PLAIN, 9));
        jTextArea.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        
        jButtonEnd = new JButton("CLOSE");
        jButtonEnd.setFont(new Font("맑은 고딕", Font.BOLD, 26));
        jButtonEnd.setForeground(Color.red);
        jButtonEnd.setBackground(Color.black);
        jButtonEnd.setMnemonic(KeyEvent.VK_Q);
        jButtonEnd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.gc();
                System.exit(0);
            }
        });
        
        jButtonStart = new JButton("START");
        jButtonStart.setFont(new Font("맑은 고딕", Font.BOLD, 26));
        jButtonStart.setForeground(Color.green);
        jButtonStart.setBackground(Color.black);
        jButtonStart.setMnemonic(KeyEvent.VK_P);
        jButtonStart.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
            	jLabelStatVal.setText("ON");
            	jLabelStatVal.setForeground(Color.green);
            	jButtonStartActionPerformed(evt, dataList);
            }
        });
        
        jButtonIconCheckAxis = new JButton("달력인식");
        jButtonIconCheckAxis.setFont(new Font("맑은 고딕", Font.BOLD, 13));
        jButtonIconCheckAxis.setBackground(Color.black);
        jButtonIconCheckAxis.setForeground(Color.yellow);
        jButtonIconCheckAxis.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
            	jButtonIconCheckActionPerformed(evt);
            }
        });
        
        jButtonGetCalIconAxis = new JButton("좌표얻기");
        jButtonGetCalIconAxis.setFont(new Font("맑은 고딕", Font.BOLD, 13));
        jButtonGetCalIconAxis.setBackground(Color.black);
        jButtonGetCalIconAxis.setForeground(Color.yellow);
        jButtonGetCalIconAxis.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
            	jButtonGetCalIconAxisActionPerformed(evt);
            }
        });
        
        jButtonGetAxis = new JButton("좌표얻기");
        jButtonGetAxis.setFont(new Font("맑은 고딕", Font.BOLD, 13));
        jButtonGetAxis.setBackground(Color.black);
        jButtonGetAxis.setForeground(Color.yellow);
        jButtonGetAxis.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
            	jButtonGetAxisActionPerformed(evt);
            }
        });
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(jFrame.getContentPane());
        jFrame.getContentPane().setLayout(layout);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                        	.addComponent(jButtonStart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButtonIconCheckAxis, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButtonEnd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            //.addComponent(jLabelEmpty)
                        )
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabelStat)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabelStatVal)
                            .addComponent(jLabelEmpty)
                        )
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabelIconStat)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabelIconStatVal)
                            .addComponent(jLabelEmpty)
                        )
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabelUserAuth)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabelUserAuthVal)
                            .addComponent(jLabelEmpty)
                        )
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabelCalIconAxisStart)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextCalIconX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabelCalIconComma)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextCalIconY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabelCalIconAxisEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButtonGetCalIconAxis, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelEmpty)
                        )
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabelAxisStart)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextCalX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabelComma)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextCalY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabelAxisEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButtonGetAxis, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelEmpty)
                        )
                        .addComponent(jLabelManual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabelDev)
                            .addGap(0, 0, Short.MAX_VALUE)))
                    .addContainerGap())
                .addGroup(layout.createSequentialGroup()
                    .addGap(24, 24, 24)
                    .addComponent(jLabelTitle)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(6, 6, 6)
                    .addComponent(jLabelTitle)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonStart)
                        .addComponent(jButtonIconCheckAxis)
                        .addComponent(jButtonEnd)
                        //.addComponent(jLabelEmpty)
                    )
                    .addGap(11, 11, 11)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabelStat)
                        .addComponent(jLabelStatVal)
                        .addComponent(jLabelEmpty)
                    )
                    .addGap(9, 9, 9)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabelIconStat)
                        .addComponent(jLabelIconStatVal)
                        .addComponent(jLabelEmpty)
                    )
                    .addGap(9, 9, 9)
					    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
					    .addComponent(jLabelUserAuth)
					    .addComponent(jLabelUserAuthVal)
					    .addComponent(jLabelEmpty)
					)
                    .addGap(3, 3, 3)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabelCalIconAxisStart)
                        .addComponent(jTextCalIconX)
                        .addComponent(jLabelCalIconComma)
                        .addComponent(jTextCalIconY)
                        .addComponent(jLabelCalIconAxisEnd)
                        .addComponent(jButtonGetCalIconAxis)
                        .addComponent(jLabelEmpty)
                    )
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabelAxisStart)
                        .addComponent(jTextCalX)
                        .addComponent(jLabelComma)
                        .addComponent(jTextCalY)
                        .addComponent(jLabelAxisEnd)
                        .addComponent(jButtonGetAxis)
                        .addComponent(jLabelEmpty)
                    )
                    .addComponent(jLabelManual)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelDev)
                    .addGap(3, 3, 3))
                    
        );
        jFrame.setSize(413,525);//420,430 -> 413,422 -> 413,455(420,427 background) -> 413,525(420,497 background)
        jFrame.setLocation(1000,500);
        jFrame.setVisible(true);
        jFrame.setResizable(false);
        
        try {
            process();
        } catch (Exception e) {
        	System.out.println("["+getTime()+"] [ERROR] ["+e.toString()+"]");
        }
    }

	public void process() throws Exception {   
        this.start();
        rob = new Robot();
        
        int keyInput[] = {
        	KeyEvent.VK_OPEN_BRACKET,
            KeyEvent.VK_S,
            KeyEvent.VK_A,
            KeyEvent.VK_M,
            KeyEvent.VK_P,
            KeyEvent.VK_L,
            KeyEvent.VK_E,
            KeyEvent.VK_SPACE,
            KeyEvent.VK_D,
            KeyEvent.VK_A,
            KeyEvent.VK_T,
            KeyEvent.VK_A,
            KeyEvent.VK_SPACE,
            KeyEvent.VK_1,
            KeyEvent.VK_CLOSE_BRACKET
        };
        rob.delay(800);
        jTextArea.requestFocus();
        for (int i = 0; i < keyInput.length; i++) {
            rob.keyPress(keyInput[i]);
            rob.delay(60);
        }
        rob.keyPress(KeyEvent.VK_ENTER);
		dataList = getData();
		displaySample(dataList);
		
		/*imagefind = new ImageFind();
		String calImgAxis = imagefind.printFindData();
		String[] calAxis = calImgAxis.split(",");
		String calX = calAxis[0];
		String calY = calAxis[1];
		String print = "["+getTime()+"]  [INFO]  ["+"##### CALENDAR IMG AXIS : ("+calX+","+calY+")]";
		System.out.println(print);
		jTextArea.setText(jTextArea.getText()+"\n"+print);
    	jTextArea.setCaretPosition(jTextArea.getText().length() - 1);*/
		
        this.stop();
    }
    
	private List<Map<String, String>> getData() {
    	HashMap<String, String> dataMap = new HashMap<String, String>();
    	InputStream in = null;
    	BufferedReader br = null;
    	InputStreamReader isr = null;

    	try {
    		String current = new File( "." ).getCanonicalPath();
    		System.out.println("["+getTime()+"]  [INFO]  ["+"##### Current path:"+current+"]");
    		//String dataPath = "C:\\eclipse_luna\\workspace\\mikhail-macro\\data\\data.txt";
    		String dataPath = current + File.separator+"data"+File.separator+"data.txt";
    		File file = new File(dataPath);
			
    		in = new FileInputStream(file);
            isr = new InputStreamReader(in);
            br = new BufferedReader(isr);
            
            String line;
            while((line = br.readLine()) != null){
            	dataMap = new HashMap<String, String>();
            	String resultLine[] = line.split("@");
            	if(resultLine.length == 2){
            		line = line + " ";
            		String resultLineAdd[] = line.split("/");
            		dataMap.put("NAME", resultLineAdd[0].trim());
            		dataMap.put("METHOD", resultLineAdd[1].trim());
            		dataMap.put("CONTENTS", resultLineAdd[2].trim());
            	}else{
            		dataMap.put("NAME", resultLine[0].trim());
            		dataMap.put("METHOD", resultLine[1].trim());
            		dataMap.put("CONTENTS", resultLine[2].trim());
            	}
            	dataList.add(dataMap);
            }
            System.out.println("["+getTime()+"]  [INFO]  ["+"##### dataList : "+dataList.toString()+"]\n");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			String print = "["+getTime()+"] [ERROR] ["+e.toString()+"]";
			System.out.println(print);
			jTextArea.setText(jTextArea.getText()+"\n"+print);
	    	jTextArea.setCaretPosition(jTextArea.getText().length() - 1);
		} catch (IOException e) {
			e.printStackTrace();
			String print = "["+getTime()+"] [ERROR] ["+e.toString()+"]";
			System.out.println(print);
			jTextArea.setText(jTextArea.getText()+"\n"+print);
	    	jTextArea.setCaretPosition(jTextArea.getText().length() - 1);
		} finally {
            try{
            	br.close();
            	isr.close();
                in.close();
            } catch(IOException io) {
            	io.printStackTrace();
            	String print = "["+getTime()+"] [ERROR] ["+io.toString()+"]";
    			System.out.println(print);
    			jTextArea.setText(jTextArea.getText()+"\n"+print);
    	    	jTextArea.setCaretPosition(jTextArea.getText().length() - 1);
            }
        }
    	return dataList;
	}
	
	private void displaySample(List<Map<String, String>> dataList) throws Exception {
    	TodayWorkingUtil.copy(" - 이름 : "+dataList.get(0).get("NAME"));
        TodayWorkingUtil.paste();
        rob.delay(100);
        rob.keyPress(KeyEvent.VK_ENTER);
        TodayWorkingUtil.copy(" - 확인방법 : "+dataList.get(0).get("METHOD"));
        TodayWorkingUtil.paste();
        rob.delay(100);
        rob.keyPress(KeyEvent.VK_ENTER);
        TodayWorkingUtil.copy(" - 내용 : "+dataList.get(0).get("CONTENTS"));
        TodayWorkingUtil.paste();
        rob.delay(100);
        rob.keyPress(KeyEvent.VK_ENTER);
        TodayWorkingUtil.copy(" - 총 입력 대상 건수 : "+dataList.size()+"\n");
        TodayWorkingUtil.paste();
        System.out.println("["+getTime()+"]  [INFO]  ["+"##### 총 입력 대상 건수 : "+dataList.size()+"]");
	}
	
	private void copyPaste(String value) throws Exception {
    	TodayWorkingUtil.copy(value);
    	rob.delay(50);
        TodayWorkingUtil.paste();
	}
	
	private void jButtonStartActionPerformed(java.awt.event.MouseEvent evt, List<Map<String, String>> dataList) {  
//		jTextArea.setText(jTextArea.getText()+"\n.....LOG TRACE.....");
//		jTextArea.setCaretPosition(jTextArea.getText().length() - 1);
		String addMsg = "";
		if(imageCheck){
			addMsg = "(달력인식:OK)";
		}else{
			addMsg = "(달력인식:FAIL)";
		}
        int n = JOptionPane.showOptionDialog(jFrame, "데이터가 정상적으로 세팅되었는지 확인하세요\n"+addMsg, "실행", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);

        if ((n == 0) && (this.imageCheck) && (this.userAuth)) {
            try {
            	rob.delay(1000);
            	File propFile = new File(propPath);
            	Properties prop = new Properties();
            	prop.load(new FileInputStream(propFile));
            	int xd = 1;
	            int yd = 1;
	            int xi = 1;
	            int yi = 1;
	            String xStr = jTextCalX.getText().toString().trim(); 
            	String yStr = jTextCalY.getText().toString().trim(); 
            	String xIconStr = jTextCalIconX.getText().toString().trim(); 
            	String yIconStr = jTextCalIconY.getText().toString().trim(); 
            	
	            
            	if(!"".equals(xStr)&&!"".equals(yStr)&&!"".equals(xIconStr)&&!"".equals(yIconStr)){
            		String print = "["+getTime()+"]  [INFO]  ["+"##### STATUS ON]";
	            	System.out.println(print);
	            	jTextArea.setText(jTextArea.getText()+"\n"+print);
	            	jTextArea.setCaretPosition(jTextArea.getText().length() - 1);
	            	xd = Integer.parseInt(xStr);
	            	yd = Integer.parseInt(yStr);
	            	xi = Integer.parseInt(xIconStr);
	            	yi = Integer.parseInt(yIconStr);
            	}else{
            		jLabelStatVal.setText("날짜 좌표를 입력하셔야 해요 *^.^*");
	                jLabelStatVal.setForeground(Color.red);
	            	return;
            	}
            	
            	/*if("".equals(xStr) && "".equals(yStr)){
	            	jLabelStatVal.setText("날짜 좌표를 입력하셔야 해요 *^.^*");
	                jLabelStatVal.setForeground(Color.red);
	            	return;
	            }else if("".equals(xStr)&&!"".equals(yStr)){
	            	jLabelStatVal.setText("하나만 더요! ^^/");
	                jLabelStatVal.setForeground(Color.red);
	            	return;
	            }else if("".equals(yStr)&&!"".equals(xStr)){
	            	jLabelStatVal.setText("하나만 더요! ^^/");
	                jLabelStatVal.setForeground(Color.red);
	            	return;
	            }else{
	            	String print = "["+getTime()+"]  [INFO]  ["+"##### STATUS ON]";
	            	System.out.println(print);
	            	jTextArea.setText(jTextArea.getText()+"\n"+print);
	            	jTextArea.setCaretPosition(jTextArea.getText().length() - 1);
	            	xd = Integer.parseInt(xStr);
	            	yd = Integer.parseInt(yStr);
	            }*/
            	
	            
            	for (int i = 0; i < dataList.size(); i++) {
            		Map<String, String> dataMap = new HashMap<String, String>();
            		dataMap = dataList.get(i);
            		
            		/*취업처리조회 메뉴 클릭*/
                	rob.mouseMove(toInt(prop.get("select.menu.x").toString().trim()), toInt(prop.get("select.menu.y").toString().trim()));
    	            rob.mousePress(InputEvent.BUTTON1_MASK);
    	            rob.delay(100);
    	            rob.mouseRelease(InputEvent.BUTTON1_MASK);
    	            rob.delay(toInt(prop.get("menu.click.ms").toString().trim()));//3000
            		
            		/*구직자 성명 입력*/
    	            rob.mouseMove(toInt(prop.get("name.input.x").toString().trim()), toInt(prop.get("name.input.y").toString().trim()));
    	            rob.delay(200);
    	            rob.mousePress(InputEvent.BUTTON1_MASK);
    	            rob.delay(100);
    	            rob.mouseRelease(InputEvent.BUTTON1_MASK);
    	            rob.delay(100);
    	            copyPaste(dataMap.get("NAME").toString().trim());
    	            //rob.keyPress(KeyEvent.VK_ENTER);
    	            rob.delay(100);
    	            
    	            /*검색 버튼 클릭*/
    	            rob.mouseMove(toInt(prop.get("search.click.x").toString().trim()), toInt(prop.get("search.click.y").toString().trim()));
    	            rob.delay(100);
    	            rob.mousePress(InputEvent.BUTTON1_MASK);
    	            rob.delay(100);
    	            rob.mouseRelease(InputEvent.BUTTON1_MASK);
    	            rob.delay(toInt(prop.get("name.search.ms").toString().trim()));
    	            
    	            /*취업처리 클릭하여 새창 열기*/
    	            rob.mouseMove(toInt(prop.get("window.open.x").toString().trim()), toInt(prop.get("window.open.y").toString().trim()));
    	            rob.delay(200);
    	            rob.mousePress(InputEvent.BUTTON1_MASK);
    	            rob.delay(100);
    	            rob.mouseRelease(InputEvent.BUTTON1_MASK);
    	            rob.delay(toInt(prop.get("window.open.ms").toString().trim()));
    	            
    	            /*새창 선택*/
    	            rob.mouseMove(toInt(prop.get("window.click.x").toString().trim()), toInt(prop.get("window.click.y").toString().trim()));
    	            rob.delay(200);
    	            rob.mousePress(InputEvent.BUTTON1_MASK);
    	            rob.delay(100);
    	            rob.mouseRelease(InputEvent.BUTTON1_MASK);
    	            rob.delay(100);
    	            
    	            /*달력 이미지 찾아 좌표 구하기*/
    	            imagefind = new ImageFind();
    	    		String calImgAxis = imagefind.printFindData();
    	    		String[] calAxis = calImgAxis.split(",");
    	    		String calX = calAxis[0];
    	    		String calY = calAxis[1];
    	    		String print = "["+getTime()+"] [DEBUG] ["+"##### 달력icon 최상단 좌측 좌표 : "+calImgAxis+"]";
	            	System.out.println(print);
	            	jTextArea.setText(jTextArea.getText()+"\n"+print);
	            	jTextArea.setCaretPosition(jTextArea.getText().length() - 1);
    	    		int nowX = Integer.parseInt(calX) + 7;
    	    		int diffX = nowX - xi;
    	    		int nowY = Integer.parseInt(calY) + 6;
    	    		int diffY = nowY - yi;
    	            
    	            /*달력 클릭 및 날짜 선택*/
    	            //rob.mouseMove(toInt(prop.get("calendar.click.x").toString()), toInt(prop.get("calendar.click.y").toString()));
    	    		rob.mouseMove((xi + diffX), (yi + diffY));
    	            rob.delay(200);
    	            rob.mousePress(InputEvent.BUTTON1_MASK);
    	            rob.delay(100);
    	            rob.mouseRelease(InputEvent.BUTTON1_MASK);
    	            rob.delay(100);
    	            //rob.mouseMove(xd, yd);//당월 기본에 일자 선택은 해당좌표를 사전에 입력받아 선택
    	            rob.mouseMove((xd + diffX), (yd + diffY));//당월 기본에 일자 선택은 해당좌표를 사전에 입력받아 선택
    	            rob.delay(200);
    	            rob.mousePress(InputEvent.BUTTON1_MASK);
    	            rob.delay(100);
    	            rob.mouseRelease(InputEvent.BUTTON1_MASK);
    	            //System.out.println("calendar/day : "+(xi + diffX)+","+(yi + diffY)+"/"+(xd + diffX)+","+(yd + diffY));
    	            print = "["+getTime()+"] [DEBUG] ["+"##### calendar/day : "+(xi + diffX)+","+(yi + diffY)+"/"+(xd + diffX)+","+(yd + diffY)+"]";
	            	System.out.println(print);
	            	jTextArea.setText(jTextArea.getText()+"\n"+print);
	            	jTextArea.setCaretPosition(jTextArea.getText().length() - 1);
    	            rob.delay(100);
    	            
    	            /*근무확인방법 선택*/
    	            if("모바일".equals(dataMap.get("METHOD").toString().trim())){
    	            	//rob.mouseMove(toInt(prop.get("check.method.mobile.x").toString())+diffX, toInt(prop.get("check.method.mobile.y").toString())+diffY);
    	            	//모바일 위치는 달력기준 x축 +206 / y축 +30
    	            	rob.mouseMove((xi + diffX + (toInt(prop.get("check.method.mobile.x").toString().trim())-toInt(prop.get("calendar.click.x").toString().trim()))), (yi + diffY + (toInt(prop.get("check.method.mobile.y").toString().trim())-toInt(prop.get("calendar.click.y").toString().trim()))));
        	            rob.delay(200);
        	            rob.mousePress(InputEvent.BUTTON1_MASK);
        	            rob.delay(100);
        	            rob.mouseRelease(InputEvent.BUTTON1_MASK);
        	            rob.delay(100);
    	            }else{//유선
    	            	//rob.mouseMove(toInt(prop.get("check.method.tel.x").toString())+diffX, toInt(prop.get("check.method.tel.y").toString())+diffY);
    	            	//유선   위치는 달력기준 x축 +135 / y축 +30
    	            	rob.mouseMove((xi + diffX + (toInt(prop.get("check.method.tel.x").toString().trim())-toInt(prop.get("calendar.click.x").toString().trim()))), (yi + diffY + (toInt(prop.get("check.method.tel.y").toString().trim())-toInt(prop.get("calendar.click.y").toString().trim()))));
        	            rob.delay(200);
        	            rob.mousePress(InputEvent.BUTTON1_MASK);
        	            rob.delay(100);
        	            rob.mouseRelease(InputEvent.BUTTON1_MASK);
        	            rob.delay(100);
    	            }
    	            
    	            /*내용 입력*/
    	            //rob.mouseMove(toInt(prop.get("contents.input.x").toString())+diffX, toInt(prop.get("contents.input.y").toString())+diffY);
    	            //내용 입력 위치는 달력기준 x축 -74 / y축 +49
    	            rob.mouseMove((xi + diffX + (toInt(prop.get("contents.input.x").toString().trim())-toInt(prop.get("calendar.click.x").toString().trim()))), (yi + diffY + (toInt(prop.get("contents.input.y").toString().trim())-toInt(prop.get("calendar.click.y").toString().trim()))));
    	            rob.delay(200);
    	            rob.mousePress(InputEvent.BUTTON1_MASK);
    	            rob.delay(100);
    	            rob.mouseRelease(InputEvent.BUTTON1_MASK);
    	            rob.delay(100);
    	            copyPaste(dataMap.get("CONTENTS").toString().trim());
    	            
    	            /*등록버튼 클릭*/
    	            //rob.mouseMove(toInt(prop.get("save.click.x").toString())+diffX, toInt(prop.get("save.click.y").toString())+diffY);
    	            //등록 버튼 위치는 달력기준 x축 +80 / y축 +105
    	            rob.mouseMove((xi + diffX + (toInt(prop.get("save.click.x").toString().trim())-toInt(prop.get("calendar.click.x").toString().trim()))), (yi + diffY + (toInt(prop.get("save.click.y").toString().trim())-toInt(prop.get("calendar.click.y").toString().trim()))));
    	            rob.delay(200);
    	            rob.mousePress(InputEvent.BUTTON1_MASK);
    	            rob.delay(100);
    	            rob.mouseRelease(InputEvent.BUTTON1_MASK);
    	            rob.delay(300);
    	            
    	            /*확인버튼 클릭*/
    	            //rob.delay(toInt(prop.get("ok.click.ms").toString().trim()));
    	            rob.keyPress(KeyEvent.VK_SPACE);
    	            rob.delay(100);
    	            rob.mouseMove(toInt(prop.get("ok.click.x").toString().trim()), toInt(prop.get("ok.click.y").toString().trim()));
    	            rob.delay(toInt(prop.get("ok.click.ms").toString().trim()));
    	            rob.mousePress(InputEvent.BUTTON1_MASK);
    	            rob.delay(100);
    	            rob.mouseRelease(InputEvent.BUTTON1_MASK);
    	            rob.delay(toInt(prop.get("ok.release.ms").toString()));
    	            
    	            print = "["+getTime()+"]  [INFO]  ["+"##### "+(i+1)+") "+dataMap.get("NAME")+" 입력 완료]";
    	            System.out.println(print);
    	            jTextArea.setText(jTextArea.getText()+"\n"+print);
    	            jTextArea.setCaretPosition(jTextArea.getText().length() - 1);
				}
            	
            	String print = "["+getTime()+"]  [INFO]  ["+"##### 출근입력 : "+dataList.size()+"명]";
            	System.out.println(print);
            	jTextArea.setText(jTextArea.getText()+"\n"+print);
            	jTextArea.setCaretPosition(jTextArea.getText().length() - 1);
            	jLabelStatVal.setText("FINISH");
            	jLabelStatVal.setForeground(Color.green);
            	print = "["+getTime()+"]  [INFO]  ["+"##### STATUS FINISH]";
            	System.out.println(print);
            	jTextArea.setText(jTextArea.getText()+"\n"+print);
            	jTextArea.setCaretPosition(jTextArea.getText().length() - 1);
	        } catch (AWTException e) {
	            e.printStackTrace();
	            String print = "["+getTime()+"] [ERROR] ["+e.toString()+"]";
	            System.out.println(print);
	            jTextArea.setText(jTextArea.getText()+"\n"+print);
	            jTextArea.setCaretPosition(jTextArea.getText().length() - 1);
	        } catch (Exception e) {
				e.printStackTrace();
				String print = "["+getTime()+"] [ERROR] ["+e.toString()+"]";
	            System.out.println(print);
	            jTextArea.setText(jTextArea.getText()+"\n"+print);
	            jTextArea.setCaretPosition(jTextArea.getText().length() - 1);
			}    
        } else {
        	if ((n == 0) && (!imageCheck) && (userAuth)){
        		JOptionPane.showOptionDialog(jFrame, "달력인식 상태를 확인하세요.", "경고", JOptionPane.CLOSED_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);//DEFAULT_OPTION //WARNING_MESSAGE
        	}else if ((n == 0) && (!userAuth) && (imageCheck)) {
			    JOptionPane.showOptionDialog(jFrame, "인증되지 않은 사용자입니다.", "경고", JOptionPane.CLOSED_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
			}else if ((n == 0) && (!userAuth) && (!this.imageCheck)) {
			    JOptionPane.showOptionDialog(jFrame, "달력인식 및 사용자인증 상태를 확인하세요.", "경고", JOptionPane.CLOSED_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
			}
			jLabelStatVal.setText("OFF");
			jLabelStatVal.setForeground(Color.red);
			String print = "[" + getTime() + "]  [INFO]  [" + "##### STATUS OFF]";
			System.out.println(print);
			jTextArea.setText(this.jTextArea.getText() + "\n" + print);
			jTextArea.setCaretPosition(this.jTextArea.getText().length() - 1);
        }
    }  
	
	private void jButtonIconCheckActionPerformed(MouseEvent evt){
		imagefind = new ImageFind();
		String calImgAxis = imagefind.printFindData();
		String print = "";
		if(!"0,0".equals(calImgAxis)){
			jLabelIconStatVal.setText("OK");
        	jLabelIconStatVal.setForeground(Color.green);
        	imageCheck = true;
        	print = "["+getTime()+"]  [INFO]  ["+"##### IMAGE RECOGNITION SUCCESS : "+calImgAxis+"]";
			System.out.println(print);
			jTextArea.setText(jTextArea.getText()+"\n"+print);
	    	jTextArea.setCaretPosition(jTextArea.getText().length() - 1);
		}else{
			jLabelIconStatVal.setText("FAIL");
        	jLabelIconStatVal.setForeground(Color.red);
        	imageCheck = false;
        	print = "["+getTime()+"] [WARN] ["+"##### IMAGE RECOGNITION FAIL : "+calImgAxis+"]";
        	System.out.println(print);
        	jTextArea.setText(jTextArea.getText()+"\n"+print);
	    	jTextArea.setCaretPosition(jTextArea.getText().length() - 1);
		}
		
		try
	    {
			String localIp = TodayWorkingUtil.getLocalServerIp();
			print = "[" + getTime() + "] [DEBUG] [" + "##### LOCAL IP : " + localIp + "]";
			System.out.println(print);
			String hostName = TodayWorkingUtil.getHostName();
			print = "[" + getTime() + "] [DEBUG] [" + "##### HOST NAME : " + hostName + "]";
			System.out.println(print);
			userAuth = TodayWorkingUtil.checkAuth(localIp, hostName);

			if (userAuth) {
				jLabelUserAuthVal.setText("ALLOW");
				jLabelUserAuthVal.setForeground(Color.green);
			} else {
				jLabelUserAuthVal.setText("DENY");
				jLabelUserAuthVal.setForeground(Color.red);
			}
			print = "[" + getTime() + "]  [INFO]  [" + "##### USER AUTH : " + userAuth + "]";
			jTextArea.setText(jTextArea.getText() + "\n" + print);
			jTextArea.setCaretPosition(jTextArea.getText().length() - 1);
    	} catch (SocketException e) {
    		print = "[" + getTime() + "] [ERROR] [" + "##### GET LOCAL IP FAIL : " + e.toString() + "]";
    		System.out.println(print);
    		jTextArea.setText(jTextArea.getText() + "\n" + print);
    		jTextArea.setCaretPosition(jTextArea.getText().length() - 1);
    	} catch (UnknownHostException e) {
    		print = "[" + getTime() + "] [ERROR] [" + "##### GET HOST NAME FAIL : " + e.toString() + "]";
    		System.out.println(print);
    		jTextArea.setText(jTextArea.getText() + "\n" + print);
    		jTextArea.setCaretPosition(jTextArea.getText().length() - 1);
    	}
	}

	
	private void jButtonGetAxisActionPerformed(MouseEvent evt) {
		if(evt.getButton() == MouseEvent.BUTTON1){
			PointerInfo pi = MouseInfo.getPointerInfo();
			int x = pi.getLocation().x;
			int y = pi.getLocation().y;
			jTextCalX.setText(Integer.toString(x));
			jTextCalY.setText(Integer.toString(y));
		}
	}
	private void jButtonGetCalIconAxisActionPerformed(MouseEvent evt) {
		if(evt.getButton() == MouseEvent.BUTTON1){
			PointerInfo pi = MouseInfo.getPointerInfo();
			int x = pi.getLocation().x;
			int y = pi.getLocation().y;
			jTextCalIconX.setText(Integer.toString(x));
			jTextCalIconY.setText(Integer.toString(y));
		}
	}
	
	private int toInt(String val){
		int result = 0;
		result = Integer.parseInt(val);
		return result;
	}
	
	private String getTime(){
		String result = "";
		Date today = new Date();
		SimpleDateFormat date = new SimpleDateFormat("yyyy.MM.dd");
	    SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss.SSS");//hh:mm:ss a (AM/PM)
	    result = date.format(today) + " " + time.format(today);
		//return result;
	    return result.substring(2);
	}
    
    public static void main(String args[]) throws Exception  {
        new TodayWorking();
    }
}