package MesMan;

import java.util.Stack;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import MesMan.MenuBar.FileMenu;
import MesMan.MenuBar.HelpMenu;
import MesMan.MenuBar.LanguageMenu;
import MesMan.MenuBar.MenuBar;
import MesMan.MenuBar.SettingMenu;
import MesMan.MenuBar.ToolsMenu;

/**
 * メッセージ管理クラス.
 * @author t_sato
 * 
 *
 */
public class MesMan extends JFrame implements ActionListener, WindowListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 715723283093561615L;
	
	private MessageManager mesman;
	private MenuBar menubar;
	private FileMenu fileMenu;
	private SettingMenu settingMenu;
	private LanguageMenu languageMenu;
	private ToolsMenu toolsMenu;
	private HelpMenu helpMenu;
	private TableMenu tableMenu;
	
	private String FRAME_TITLE = "Message Manager";
	
	
	public static void main(String[] args)
	{	
		MesMan mesMan = new MesMan();
	}
	
	/**
	 * コンストラクタ.
	 */
	public MesMan()
	{
		addWindowListener(this);
		
		mesman = new MessageManager("/res/MesTableDefine.bin");
		mesman.setLanguageNo(SettingMenu.getDefaultLanguage("./config.txt"));
		
		setTitle(FRAME_TITLE);
		
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Rectangle rec = env.getMaximumWindowBounds();
		int width = (int)rec.getWidth() / 2;
		int height = (int)rec.getHeight() / 2;
		int x = ((int)rec.getWidth() / 2) - (width / 2);
		int y = ((int)rec.getHeight() / 2) - (height / 2);
		setBounds( x, y, width, height );		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//// menu bar
		menubar = new MenuBar(this);

		// size
		Dimension size = getContentPane().getSize();
		tableMenu = new TableMenu(mesman, size.width, size.height);
		add(tableMenu.getComponent(), BorderLayout.CENTER);
		
		{
			settingMenu = new SettingMenu(this, mesman, "./config.txt");
			fileMenu = new FileMenu(this, mesman, tableMenu, settingMenu);
			languageMenu = new LanguageMenu(this, mesman);
			toolsMenu = new ToolsMenu(this, mesman, tableMenu);
			helpMenu = new HelpMenu(this, mesman);
		}
		
		menubar.add(fileMenu);
		menubar.add(settingMenu);
		menubar.add(languageMenu);
		menubar.add(toolsMenu);
		menubar.add(helpMenu);
		
		setJMenuBar(menubar.getMenuBar());
		setVisible(true);
		
		getContentPane().validate();
		
		// resize listener
		{
			Stack<TableEx> tables = new Stack<TableEx>();
			tables.push(this.tableMenu.getTagTable());
			tables.push(this.tableMenu.getMesTable());
			tables.push(this.tableMenu.getCharsizeTable());
			tables.push(this.tableMenu.getResultTable());
			addComponentListener(new MyComponentListener(getContentPane(), tables));
		}
	}
	
	public void actionPerformed(ActionEvent e)
	{
	}

	public void windowActivated(WindowEvent arg0) {}
	public void windowClosed(WindowEvent arg0) {}
	public void windowClosing(WindowEvent arg0) {
		// 終了時処理
		this.settingMenu.save();
	}
	
	public void windowDeactivated(WindowEvent arg0) {}
	public void windowDeiconified(WindowEvent arg0) {}
	public void windowIconified(WindowEvent arg0) {}
	public void windowOpened(WindowEvent arg0) {}
}
