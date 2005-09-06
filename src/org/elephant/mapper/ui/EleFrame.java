package org.elephant.mapper.ui;

import java.awt.AWTEvent;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.LookAndFeel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.KeyStroke;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.MenuEvent;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.MenuListener;

import com.borland.jbcl.layout.VerticalFlowLayout;
import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;
import org.elephant.mapper.helper.RoomHelper;
import org.elephant.mapper.helper.ExitHelper;
import org.elephant.mapper.EleMap;
import org.elephant.mapper.EleMappableCollection;
import org.elephant.mapper.EleConstants;
import org.elephant.mapper.EleUtils;
import org.elephant.mapper.Room;
import org.elephant.mapper.FileChooser;
import org.elephant.mapper.DocumentHandler;
import org.elephant.mapper.EleMapExportException;
import org.elephant.mapper.EleMappable;
import org.elephant.mapper.Exit;
import org.elephant.mapper.EleHashtable;
import org.elephant.mapper.LoadedObject;
import org.elephant.mapper.Function;

/**
 * The graphical part of the Mapper, this class also contains a lot
 * of the logic for creating a map.
 * <p>
 * Copyright (c) 2001
 * <p>
 * @author James Cook
 * @version 1.0
 */
public class EleFrame extends JFrame {
    /* todo Stuff still to do...
     * 1) add_simple_verb (/wizards/ornan/examples/simple_verb)
     * 2) add_guard
     * 3) auto generate exits to adjacent rooms
     * 4) MonsterViewer
     */
    private static final int TOOL_SELECT = 1;
    private static final int TOOL_DELETE = 2;
    private static final int TOOL_ADD_ROOM = 3;
    private static final int TOOL_ADD_EXIT = 4;
    private static final int DEFAULT_ROOM_PANE = 0;
    private static final int DEFAULT_EXIT_PANE = 0;
    private static final int NUMBER_OF_ROOMS_ON_MAP = 300;
    private static final int ZOOM_LEVEL = 2;
    private static final String RESOURCES = "resources/";
    private static final String MAPS = "maps/";
    private static final String IMAGES = RESOURCES + "images/";

    private String _eleImage = IMAGES + "Ele.gif";
    private String _levelText = "Level ";
    private int _currentTool;
    private JButton _currentButton;
    private EleMap _eleMap;
    private EleMappableCollection<EleMappable> _stuffToMove;
    private RoomHelper roomHelper = new RoomHelper();
    private ExitHelper exitHelper = new ExitHelper();
    private Graphics _mapGraphics;
    private Point _exitStartPoint;
    private boolean _makingExit;
    private boolean _pasting;
    private boolean _movingStuff;
    private File _currentDir;
    private File _currentFile;
    private Dimension _size = new Dimension();
    private JRadioButton _senseSelected = null;

    // Possible Look & Feels
    private String _mac      = "com.sun.java.swing.plaf.mac.MacLookAndFeel";
    private String _metal    = "javax.swing.plaf.metal.MetalLookAndFeel";
    private String _motif    = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
    private String _windows  = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";

    // The current Look & Feel
    private String _currentLookAndFeel = _metal;

    // My components
    private Border _borderButtonNormal;
    private Border _borderButtonRaised;
    private Border _borderButtonLowered;
    private Border _borderInset5;
    private ImageIcon _image1;
    private ImageIcon _image2;
    private ImageIcon _image3;
    private ImageIcon _image4;
    private ImageIcon _image5;
    private ImageIcon _image6;
    private ImageIcon _image7;
    private ImageIcon _image8;
    private ImageIcon _image9;

    // Generated Components
    private JPanel pnlContent;
    private JPanel pnlMap = new JPanel();
    private JPanel pnlStatusLayout = new JPanel();
    private JPanel pnlPropertiesLayout = new JPanel();
    private JPanel pnlRoomBasics = new JPanel();
    private JPanel pnlRoomBasicsLayout1 = new JPanel();
    private JPanel pnlRoomBasicsLayout2 = new JPanel();
    private JPanel pnlRoomBasicsLayout3 = new JPanel();
    private JPanel pnlRoomBasicsLayout4 = new JPanel();
    private JPanel pnlRoomBasicsLayout5 = new JPanel();
    private JPanel pnlRoomBasicsLayout6 = new JPanel();
    private JPanel pnlRoomBasicsLayout7 = new JPanel();
    private JPanel pnlRoomItems = new JPanel();
    private JPanel pnlRoomItemsLayout1 = new JPanel();
    private JPanel pnlRoomItemsLayout2 = new JPanel();
    private JPanel pnlRoomItemsLayout3 = new JPanel();
    private JPanel pnlRoomItemsLayout4 = new JPanel();
    private JPanel pnlRoomItemsLayout5 = new JPanel();
    private JPanel pnlRoomItemsLayout6 = new JPanel();
    private JPanel pnlRoomMisc = new JPanel();
    private JPanel pnlRoomMiscLayout1 = new JPanel();
    private JPanel pnlRoomMiscLayout2 = new JPanel();
    private JPanel pnlRoomMiscLayout3 = new JPanel();
    private JPanel pnlRoomMiscLayout4 = new JPanel();
    private JPanel pnlRoomMiscLayout5 = new JPanel();
    private JPanel pnlRoomSenses = new JPanel();
    private JPanel pnlRoomSensesLayout1 = new JPanel();
    private JPanel pnlRoomSensesLayout2 = new JPanel();
    private JPanel pnlRoomSensesLayout3 = new JPanel();
    private JPanel pnlRoomSensesLayout4 = new JPanel();
    private JPanel pnlRoomSensesLayout5 = new JPanel();
    private JPanel pnlRoomSensesLayout6 = new JPanel();
    private JPanel pnlRoomSensesLayout7 = new JPanel();
    private JPanel pnlRoomSensesLayout8 = new JPanel();
    private JPanel pnlRoomSensesLayout9 = new JPanel();
    private JPanel pnlRoomSensesLayout10 = new JPanel();
    private JPanel pnlRoomSensesLayout11 = new JPanel();
    private JPanel pnlRoomObjects = new JPanel();
    private JPanel pnlRoomObjectsLayout1 = new JPanel();
    private JPanel pnlRoomObjectsLayout2 = new JPanel();
    private JPanel pnlRoomObjectsLayout3 = new JPanel();
    private JPanel pnlRoomObjectsLayout4 = new JPanel();
    private JPanel pnlRoomObjectsLayout5 = new JPanel();
    private JPanel pnlRoomObjectsLayout6 = new JPanel();
    private JPanel pnlRoomObjectsLayout7 = new JPanel();
    private JPanel pnlRoomObjectsLayout8 = new JPanel();
    private JPanel pnlRoomObjectsLayout9 = new JPanel();
    private JPanel pnlRoomObjectsLayout10 = new JPanel();
    private JPanel pnlRoomGuards = new JPanel();
    private JPanel pnlRoomGuardsLayout1 = new JPanel();
    private JPanel pnlRoomGuardsLayout2 = new JPanel();
    private JPanel pnlRoomGuardsLayout3 = new JPanel();
    private JPanel pnlRoomGuardsLayout4 = new JPanel();
    private JPanel pnlRoomGuardsLayout5 = new JPanel();
    private JPanel pnlRoomGuardsLayout6 = new JPanel();
    private JPanel pnlRoomGuardsLayout7 = new JPanel();
    private JPanel pnlRoomGuardsLayout8 = new JPanel();
    private JPanel pnlRoomGuardsLayout9 = new JPanel();
    private JPanel pnlRoomGuardsLayout10 = new JPanel();
    private JPanel pnlRoomGuardsLayout11 = new JPanel();
    private JPanel pnlRoomGuardsLayout12 = new JPanel();
    private JPanel pnlRoomGuardsLayout13 = new JPanel();
    private JPanel pnlRoomGuardsLayout14 = new JPanel();
    private JPanel pnlRoomGuardsLayout15 = new JPanel();
    private JPanel pnlRoomGuardsLayout16 = new JPanel();
    private JPanel pnlRoomGuardsLayout17 = new JPanel();
    private JPanel pnlRoomGuardsLayout18 = new JPanel();
    private JPanel pnlRoomFunctions = new JPanel();
    private JPanel pnlRoomFunctionsLayout1 = new JPanel();
    private JPanel pnlRoomFunctionsLayout2 = new JPanel();
    private JPanel pnlRoomFunctionsLayout4 = new JPanel();
    private JPanel pnlRoomFunctionsLayout5 = new JPanel();
    private JPanel pnlRoomFunctionsLayout6 = new JPanel();
    private JPanel pnlRoomFunctionsLayout7 = new JPanel();
    private JPanel pnlRoomFunctionsLayout8 = new JPanel();
    private JPanel pnlRoomFunctionsLayout9 = new JPanel();
    private JPanel pnlRoomFunctionsLayout10 = new JPanel();
    private JPanel pnlRoomAdv = new JPanel();
    private JPanel pnlRoomAdvancedLayout1 = new JPanel();
    private JPanel pnlRoomAdvancedLayout2 = new JPanel();
    private JPanel pnlRoomAdvancedLayout3 = new JPanel();
    private JPanel pnlRoomAdvancedLayout4 = new JPanel();
    private JPanel pnlRoomEleMapper = new JPanel();
    private JPanel pnlRoomEleMapperLayout1 = new JPanel();
    private JPanel pnlProperties = new JPanel();
    private JPanel pnlExitSpecifics = new JPanel();
    private JPanel pnlExitDoor = new JPanel();
    private JPanel pnlExitInvisible = new JPanel();
    private JPanel pnlExitLock = new JPanel();
    private JPanel pnlExitRoomNumbers = new JPanel();
    private JPanel pnlExitMiniMap = new JPanel();
    private JPanel pnlExitSpecificsLayout1 = new JPanel();
    private JPanel pnlExitSpecificsLayout2 = new JPanel();
    private JPanel pnlExitSpecificsLayout3 = new JPanel();
    private JPanel pnlExitSpecificsLayout4 = new JPanel();
    private JPanel pnlExitSpecificsLayout5 = new JPanel();
    private JPanel pnlExitSpecificsLayout6 = new JPanel();
    private JButton btnSelect = new JButton();
    private JButton btnAddRoom = new JButton();
    private JButton btnAddExit = new JButton();
    private JButton btnDelete = new JButton();
    private JButton btnZoomIn = new JButton();
    private JButton btnZoomOut = new JButton();
    private JButton btnLevelUp = new JButton();
    private JButton btnLevelDown = new JButton();
    private JButton btnHelp = new JButton();
    private JButton btnItemAdd = new JButton();
    private JButton btnItemDelete = new JButton();
    private JButton btnSenseAdd = new JButton();
    private JButton btnSenseDelete = new JButton();
    private JButton btnObjectDelete = new JButton();
    private JButton btnObjectAdd = new JButton();
    private JButton btnRoomColour = new JButton();
    private JButton btnFunctionAdd = new JButton();
    private JButton btnFunctionDelete = new JButton();
    private JButton btnGuardAdd = new JButton();
    private JButton btnGuardDelete = new JButton();
    private BorderLayout borderLayoutStatus = new BorderLayout();
    private BorderLayout borderLayoutProperties = new BorderLayout();
    private BorderLayout borderLayout1 = new BorderLayout();
    private BorderLayout borderLayout2 = new BorderLayout();
    private BorderLayout borderLayout3 = new BorderLayout();
    private BorderLayout borderLayout4 = new BorderLayout();
    private BorderLayout borderLayout5 = new BorderLayout();
    private BorderLayout borderLayout6 = new BorderLayout();
    private BorderLayout borderLayout7 = new BorderLayout();
    private BorderLayout borderLayout8 = new BorderLayout();
    private BorderLayout borderLayout9 = new BorderLayout();
    private BorderLayout borderLayout10 = new BorderLayout();
    private BorderLayout borderLayout11 = new BorderLayout();
    private BorderLayout borderLayout12 = new BorderLayout();
    private BorderLayout borderLayout13 = new BorderLayout();
    private BorderLayout borderLayout14 = new BorderLayout();
    private BorderLayout borderLayout15 = new BorderLayout();
    private BorderLayout borderLayout16 = new BorderLayout();
    private BorderLayout borderLayout17 = new BorderLayout();
    private BorderLayout borderLayout18 = new BorderLayout();
    private BorderLayout borderLayout19 = new BorderLayout();
    private BorderLayout borderLayout20 = new BorderLayout();
    private BorderLayout borderLayout21 = new BorderLayout();
    private BorderLayout borderLayout22 = new BorderLayout();
    private BorderLayout borderLayout23 = new BorderLayout();
    private BorderLayout borderLayout24 = new BorderLayout();
    private BorderLayout borderLayout25 = new BorderLayout();
    private BorderLayout borderLayout26 = new BorderLayout();
    private BorderLayout borderLayout27 = new BorderLayout();
    private BorderLayout borderLayout28 = new BorderLayout();
    private BorderLayout borderLayout29 = new BorderLayout();
    private BorderLayout borderLayout30 = new BorderLayout();
    private BorderLayout borderLayout31 = new BorderLayout();
    private BorderLayout borderLayout32 = new BorderLayout();
    private BorderLayout borderLayout33 = new BorderLayout();
    private BorderLayout borderLayout34 = new BorderLayout();
    private BorderLayout borderLayout35 = new BorderLayout();
    private BorderLayout borderLayout36 = new BorderLayout();
    private BorderLayout borderLayout37 = new BorderLayout();
    private BorderLayout borderLayout38 = new BorderLayout();
    private BorderLayout borderLayout39 = new BorderLayout();
    private BorderLayout borderLayout40 = new BorderLayout();
    private BorderLayout borderLayout41 = new BorderLayout();
    private BorderLayout borderLayout42 = new BorderLayout();
    private BorderLayout borderLayout43 = new BorderLayout();
    private BorderLayout borderLayout44 = new BorderLayout();
    private BorderLayout borderLayout45 = new BorderLayout();
    private BorderLayout borderLayout46 = new BorderLayout();
    private BorderLayout borderLayout47 = new BorderLayout();
    private BorderLayout borderLayout48 = new BorderLayout();
    private BorderLayout borderLayout49 = new BorderLayout();
    private BorderLayout borderLayout50 = new BorderLayout();
    private BorderLayout borderLayout51 = new BorderLayout();
    private BorderLayout borderLayout53 = new BorderLayout();
    private BorderLayout borderLayout54 = new BorderLayout();
    private BorderLayout borderLayout55 = new BorderLayout();
    private BorderLayout borderLayout56 = new BorderLayout();
    private BorderLayout borderLayout57 = new BorderLayout();
    private BorderLayout borderLayout58 = new BorderLayout();
    private BorderLayout borderLayout59 = new BorderLayout();
    private BorderLayout borderLayout60 = new BorderLayout();
    private BorderLayout borderLayout61 = new BorderLayout();
    private BorderLayout borderLayout62 = new BorderLayout();
    private BorderLayout borderLayout63 = new BorderLayout();
    private BorderLayout borderLayout64 = new BorderLayout();
    private BorderLayout borderLayout65 = new BorderLayout();
    private BorderLayout borderLayout66 = new BorderLayout();
    private BorderLayout borderLayout67 = new BorderLayout();
    private BorderLayout borderLayout68 = new BorderLayout();
    private BorderLayout borderLayout69 = new BorderLayout();
    private BorderLayout borderLayout70 = new BorderLayout();
    private BorderLayout borderLayout71 = new BorderLayout();
    private VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
    private VerticalFlowLayout verticalFlowLayout2 = new VerticalFlowLayout();
    private VerticalFlowLayout verticalFlowLayout3 = new VerticalFlowLayout();
    private VerticalFlowLayout verticalFlowLayout4 = new VerticalFlowLayout();
    private VerticalFlowLayout verticalFlowLayout5 = new VerticalFlowLayout();
    private VerticalFlowLayout verticalFlowLayout6 = new VerticalFlowLayout();
    private XYLayout xYLayout2 = new XYLayout();
    private XYLayout xYLayout3 = new XYLayout();
    private XYLayout xYLayout4 = new XYLayout();
    private XYLayout xYLayout5 = new XYLayout();
    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    private GridBagLayout gridBagLayout2 = new GridBagLayout();
    private GridBagLayout gridBagLayout3 = new GridBagLayout();
    private JLabel statusBar = new JLabel();
    private JLabel statusBarLevel = new JLabel();
    private JLabel lblRoomNumber = new JLabel();
    private JLabel lblExitRoom1 = new JLabel();
    private JLabel lblExitRoom2 = new JLabel();
    private JLabel jLabel1 = new JLabel();
    private JLabel jLabel2 = new JLabel();
    private JLabel jLabel3 = new JLabel();
    private JLabel jLabel4 = new JLabel();
    private JLabel jLabel5 = new JLabel();
    private JLabel jLabel6 = new JLabel();
    private JLabel jLabel7 = new JLabel();
    private JLabel jLabel8 = new JLabel();
    private JLabel jLabel9 = new JLabel();
    private JLabel jLabel10 = new JLabel();
    private JLabel jLabel11 = new JLabel();
    private JLabel jLabel12 = new JLabel();
    private JLabel jLabel13 = new JLabel();
    private JLabel jLabel14 = new JLabel();
    private JLabel jLabel15 = new JLabel();
    private JLabel jLabel16 = new JLabel();
    private JLabel jLabel17 = new JLabel();
    private JLabel jLabel18 = new JLabel();
    private JLabel jLabel19 = new JLabel();
    private JLabel jLabel20 = new JLabel();
    private JLabel jLabel21 = new JLabel();
    private JLabel jLabel22 = new JLabel();
    private JLabel jLabel23 = new JLabel();
    private JLabel jLabel24 = new JLabel();
    private JLabel jLabel25 = new JLabel();
    private JLabel jLabel26 = new JLabel();
    private JLabel jLabel27 = new JLabel();
    private JLabel jLabel28 = new JLabel();
    private JLabel jLabel29 = new JLabel();
    private JLabel jLabel30 = new JLabel();
    private JLabel jLabel31 = new JLabel();
    private JLabel jLabel32 = new JLabel();
    private JLabel jLabel33 = new JLabel();
    private JLabel jLabel34 = new JLabel();
    private JLabel jLabel35 = new JLabel();
    private JLabel jLabel36 = new JLabel();
    private JLabel jLabel37 = new JLabel();
    private JLabel jLabel38 = new JLabel();
    private JLabel jLabel39 = new JLabel();
    private JLabel jLabel40 = new JLabel();
    private JLabel jLabel41 = new JLabel();
    private JLabel jLabel42 = new JLabel();
    private JLabel jLabel43 = new JLabel();
    private JLabel jLabel44 = new JLabel();
    private JLabel jLabel45 = new JLabel();
    private JLabel jLabel46 = new JLabel();
    private JLabel jLabel47 = new JLabel();
    private JLabel[] lblExitDirection = new JLabel[10];
    private JTabbedPane tabbedPaneRoom = new JTabbedPane();
    private JTabbedPane tabbedPaneExit = new JTabbedPane();
    private JTabbedPane tabbedPaneExitSpecifics = new JTabbedPane();
    private JTextField txtRoomName = new JTextField();
    private JTextField txtKeyId1 = new JTextField();
    private JTextField txtDoorName1 = new JTextField();
    private JTextField txtDoorName2 = new JTextField();
    private JTextField txtItemNames = new JTextField();
    private JTextField txtVisibleDefaultSense = new JTextField();
    private JTextField txtDefaultSense = new JTextField();
    private JTextField txtSenseName = new JTextField();
    private JTextField txtObjectFileName = new JTextField();
    private JTextField txtObjectMessage = new JTextField();
    private JTextField txtRoomShortDesc = new JTextField();
    private JTextField txtRoomCodeDesc = new JTextField();
    private JTextField txtKeyId2 = new JTextField();
    private JTextField txtBoundary = new JTextField();
    private JTextField txtGuardCheckInline = new JTextField();
    private JTextField txtGuardMessageInline = new JTextField();
    private JTextField txtGuardWizardInline = new JTextField();
    private JTextField txtGuardMessagePlayer = new JTextField();
    private JTextField txtGuardMessageRoom = new JTextField();
    private JTextField txtDoorDescRoom1 = new JTextField();
    private JTextField txtDoorDescRoom2 = new JTextField();
    private JTextField txtDoorOpenDescRoom1 = new JTextField();
    private JTextField txtDoorOpenDescRoom2 = new JTextField();
    private JTextField txtDoorClosedDescRoom1 = new JTextField();
    private JTextField txtDoorClosedDescRoom2 = new JTextField();
    private JTextField txtFunctionArguments = new JTextField();
    private JTextField txtFunctionName = new JTextField();
    private JTextField txtFunctionReturnType = new JTextField();
    private JTextArea txtSenseDescription = new JTextArea();
    private JTextArea txtRoomLongDesc = new JTextArea();
    private JTextArea txtItemDescription = new JTextArea();
    private JTextArea txtRoomLongDescCopy = new JTextArea();
    private JTextArea txtRoomCode = new JTextArea();
    private JTextArea txtFunctionBody = new JTextArea();
    private ButtonGroup buttonGroupExitInvis = new ButtonGroup();
    private ButtonGroup buttonGroupExitDoor = new ButtonGroup();
    private ButtonGroup buttonGroupExitLock = new ButtonGroup();
    private ButtonGroup buttonGroupRoomOutDoors = new ButtonGroup();
    private ButtonGroup buttonGroupRoomSense = new ButtonGroup();
    private ButtonGroup buttonGroupRoomObjectType = new ButtonGroup();
    private ButtonGroup buttonGroupRoomObjectLoadType = new ButtonGroup();
    private ButtonGroup buttonGrouplafMenu = new ButtonGroup();
    private ButtonGroup buttonGroupGuardCheck = new ButtonGroup();
    private ButtonGroup buttonGroupGuardMessage = new ButtonGroup();
    private ButtonGroup buttonGroupGuardWizard = new ButtonGroup();
    private JRadioButton optNotInvis = new JRadioButton();
    private JRadioButton optInvis = new JRadioButton();
    private JRadioButton optNotDoor = new JRadioButton();
    private JRadioButton optDoor = new JRadioButton();
    private JRadioButton optNotLockable = new JRadioButton();
    private JRadioButton optLockable = new JRadioButton();
    private JRadioButton optIndoors = new JRadioButton();
    private JRadioButton optOutdoors = new JRadioButton();
    private JRadioButton optSounds = new JRadioButton();
    private JRadioButton optSmells = new JRadioButton();
    private JRadioButton optObjectTypeObj = new JRadioButton();
    private JRadioButton optObjectTypeMon = new JRadioButton();
    private JRadioButton optObjectLoadUnique = new JRadioButton();
    private JRadioButton optObjectLoadPresent = new JRadioButton();
    private JRadioButton optObjectLoadTrack = new JRadioButton();
    private JRadioButton optGuardCheckDefault = new JRadioButton();
    private JRadioButton optGuardCheckGPRESENT = new JRadioButton();
    private JRadioButton optGuardCheckFunction = new JRadioButton();
    private JRadioButton optGuardCheckInline = new JRadioButton();
    private JRadioButton optGuardMessageDefault = new JRadioButton();
    private JRadioButton optGuardMessageGMSG = new JRadioButton();
    private JRadioButton optGuardMessageFunction = new JRadioButton();
    private JRadioButton optGuardMessageInline = new JRadioButton();
    private JRadioButton optGuardWizardDefault = new JRadioButton();
    private JRadioButton optGuardWizardFunction = new JRadioButton();
    private JRadioButton optGuardWizardInline = new JRadioButton();
    private JCheckBox chkInvisibleRoom1 = new JCheckBox();
    private JCheckBox chkInvisibleRoom2 = new JCheckBox();
    private JCheckBox chkConcealedRoom1 = new JCheckBox();
    private JCheckBox chkConcealedRoom2 = new JCheckBox();
    private JCheckBox chkSameKeys = new JCheckBox();
    private JCheckBox chkSameDoorName = new JCheckBox();
    private JCheckBox chkSameDoorDesc = new JCheckBox();
    private JList lstItems = new JList();
    private JList lstTerrains = new JList();
    private JList lstSenses = new JList();
    private JList lstObjects = new JList();
    private JList lstAvailableGuardObjects = new JList();
    private JList lstGuardDirections = new JList();
    private JList lstFunctions = new JList();
    private JList lstGuards = new JList();
    private JScrollPane pnlScrollMapLayout = new JScrollPane();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JScrollPane jScrollPane2 = new JScrollPane();
    private JScrollPane jScrollPane3 = new JScrollPane();
    private JScrollPane jScrollPane4 = new JScrollPane();
    private JScrollPane jScrollPane5 = new JScrollPane();
    private JScrollPane jScrollPane6 = new JScrollPane();
    private JScrollPane jScrollPane7 = new JScrollPane();
    private JScrollPane jScrollPane8 = new JScrollPane();
    private JScrollPane jScrollPane9 = new JScrollPane();
    private JScrollPane jScrollPane10 = new JScrollPane();
    private JScrollPane jScrollPane11 = new JScrollPane();
    private JScrollPane jScrollPane12 = new JScrollPane();
    private JScrollPane jScrollPane13 = new JScrollPane();
    private JScrollPane jScrollPane14 = new JScrollPane();
    private JSlider sldrLightLevel = new JSlider();
    private JComboBox selGuardCheckFunctions = new JComboBox();
    private JComboBox selGuardMessageFunctions = new JComboBox();
    private JComboBox selGuardWizardFunctions = new JComboBox();
    // Toolbars/menus
    private JToolBar toolBar1 = new JToolBar();
    private JSeparator separator1 = new JSeparator();
    private JSeparator separator2 = new JSeparator();
    private JSeparator separator3 = new JSeparator();
    private JMenuBar myMenuBar = new JMenuBar();
    private JMenu menuFile = new JMenu();
    private JMenuItem menuFileNew = new JMenuItem();
    private JMenuItem menuFileOpen = new JMenuItem();
    private JMenuItem menuFileSave = new JMenuItem();
    private JMenuItem menuFileSaveAs = new JMenuItem();
    private JMenuItem menuFileExport = new JMenuItem();
    private JMenuItem menuFileExit = new JMenuItem();
    private JMenu menuEdit = new JMenu();
    private JMenuItem menuEditCopy = new JMenuItem();
    private JMenuItem menuEditPaste = new JMenuItem();
    private JMenu menuActions = new JMenu();
    private JMenuItem menuActionSetPath = new JMenuItem();
    private JMenu menuTools = new JMenu();
    private JMenuItem menuToolSelect = new JMenuItem();
    private JMenuItem menuToolDelete = new JMenuItem();
    private JMenuItem menuToolAddRoom = new JMenuItem();
    private JMenuItem menuToolAddExit = new JMenuItem();
    private JMenuItem menuToolOptions = new JMenuItem();
    private JMenu menuView = new JMenu();
    private JCheckBoxMenuItem menuViewLower = new JCheckBoxMenuItem();
    private JCheckBoxMenuItem menuViewUpper = new JCheckBoxMenuItem();
    private JCheckBoxMenuItem menuViewProperties = new JCheckBoxMenuItem();
    private JMenu menuLaF = new JMenu();
    private JRadioButtonMenuItem menuLaFMac = new JRadioButtonMenuItem();
    private JRadioButtonMenuItem menuLaFMotif = new JRadioButtonMenuItem();
    private JRadioButtonMenuItem menuLaFWindows = new JRadioButtonMenuItem();
    private JRadioButtonMenuItem menuLaFJava = new JRadioButtonMenuItem();
    private JMenu menuHelp = new JMenu();
    private JMenuItem menuHelpAbout = new JMenuItem();
    private JPopupMenu popupMenu = new JPopupMenu();
    private JMenu menuToolLevelExitMenu = new JMenu();
    private JMenuItem menuLevelAddUp = new JMenuItem();
    private JMenuItem menuLevelDeleteUp = new JMenuItem();
    private JMenuItem menuLevelSelectUp = new JMenuItem();
    private JMenuItem menuLevelAddDown = new JMenuItem();
    private JMenuItem menuLevelDeleteDown = new JMenuItem();
    private JMenuItem menuLevelSelectDown = new JMenuItem();
    //not sorted


    //----------------------------------------------------------
    // Constructors
    //----------------------------------------------------------

    public EleFrame() {
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        try {
            jbInit();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    //-------------------------------------------------
    // Auto built functions
    //-------------------------------------------------

    /** component initialization. */
    private void jbInit() throws Exception  {
        // Setup variables
        _image1 = new ImageIcon(getClass().getResource(IMAGES + "Select.gif"));
        _image2 = new ImageIcon(getClass().getResource(IMAGES + "AddRoom.gif"));
        _image3 = new ImageIcon(getClass().getResource(IMAGES + "AddExit.gif"));
        _image4 = new ImageIcon(getClass().getResource(IMAGES + "Delete.gif"));
        _image5 = new ImageIcon(getClass().getResource(IMAGES + "ZoomIn.gif"));
        _image6 = new ImageIcon(getClass().getResource(IMAGES + "ZoomOut.gif"));
        _image7 = new ImageIcon(getClass().getResource(IMAGES + "Up.gif"));
        _image8 = new ImageIcon(getClass().getResource(IMAGES + "Down.gif"));
        _image9 = new ImageIcon(getClass().getResource(IMAGES + "Help.gif"));
        _currentDir = new File(MAPS);
        _borderButtonNormal = BorderFactory.createEmptyBorder();
        _borderButtonRaised = BorderFactory.createBevelBorder(BevelBorder.RAISED,Color.lightGray,Color.white,Color.gray,Color.lightGray);
        _borderButtonLowered = BorderFactory.createBevelBorder(BevelBorder.LOWERED,Color.lightGray,Color.white,Color.gray,Color.lightGray);
        _borderInset5 = BorderFactory.createEmptyBorder(5,5,5,5);
        pnlContent = (JPanel) getContentPane();
        Insets optionInsets = new Insets(0, 2, 0, 0);
        Insets zeroInsets = new Insets(0, 0, 0, 0);
        Font textFont = new Font("SansSerif", 0, 12);
        Font codeFont = new Font("Monospaced", 0, 11);
        Dimension buttonSize = new Dimension(22, 22);
        for (int i = 0; i < 10; i++) {
            lblExitDirection[i] = new JLabel(EleConstants.DIRECTION_ABBREV[i]);
        }

        // Set properties on this.
//        setIconImage(Toolkit.getDefaultToolkit().createImage(EleFrame.class.getResource(_eleImage))); //todo Comment out for designer
        setSize(new Dimension(586, 618));
        updateTitle();

        // map stuff
        pnlMap = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                _eleMap.paint(g, 0);
                _mapGraphics = g;
            }
        };
        pnlMap.setBackground(EleConstants.BACKGROUND_COLOUR);
        pnlMap.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                map_mouseDragged(e);
            }
        });
        pnlMap.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                map_mouseClicked(e);
            }
            public void mousePressed(MouseEvent e) {
                map_mousePressed(e);
            }
            public void mouseReleased(MouseEvent e) {
                map_mouseReleased(e);
            }
        });

        // mini map
        pnlExitMiniMap = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                paintMiniExitMap(g);
            }
        };

        // Button stuff
        btnSelect.setBorder(_borderButtonNormal);
        btnSelect.setMaximumSize(buttonSize);
        btnSelect.setMinimumSize(buttonSize);
        btnSelect.setPreferredSize(buttonSize);
        btnSelect.setToolTipText("Select");
        btnSelect.setFocusPainted(false);
        btnSelect.setIcon(_image1);
        btnSelect.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button_mouseEntered(e);
            }
            public void mouseExited(MouseEvent e) {
                button_mouseExited(e);
            }
            public void mousePressed(MouseEvent e) {
                button_mousePressed(e);
            }
            public void mouseReleased(MouseEvent e) {
                button_mouseReleased(e);
            }
        });
        btnSelect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                button_actionPerformed(btnSelect, TOOL_SELECT);
            }
        });
        btnAddRoom.setBorder(_borderButtonNormal);
        btnAddRoom.setMaximumSize(buttonSize);
        btnAddRoom.setMinimumSize(buttonSize);
        btnAddRoom.setPreferredSize(buttonSize);
        btnAddRoom.setToolTipText("Add Room");
        btnAddRoom.setFocusPainted(false);
        btnAddRoom.setIcon(_image2);
        btnAddRoom.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button_mouseEntered(e);
            }
            public void mouseExited(MouseEvent e) {
                button_mouseExited(e);
            }
            public void mousePressed(MouseEvent e) {
                button_mousePressed(e);
            }
            public void mouseReleased(MouseEvent e) {
                button_mouseReleased(e);
            }
        });
        btnAddRoom.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                button_actionPerformed(btnAddRoom, TOOL_ADD_ROOM);
            }
        });
        btnAddExit.setBorder(_borderButtonNormal);
        btnAddExit.setMaximumSize(buttonSize);
        btnAddExit.setMinimumSize(buttonSize);
        btnAddExit.setPreferredSize(buttonSize);
        btnAddExit.setToolTipText("Add Exit");
        btnAddExit.setFocusPainted(false);
        btnAddExit.setIcon(_image3);
        btnAddExit.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button_mouseEntered(e);
            }
            public void mouseExited(MouseEvent e) {
                button_mouseExited(e);
            }
            public void mousePressed(MouseEvent e) {
                button_mousePressed(e);
            }
            public void mouseReleased(MouseEvent e) {
                button_mouseReleased(e);
            }
        });
        btnAddExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                button_actionPerformed(btnAddExit, TOOL_ADD_EXIT);
            }
        });
        btnDelete.setBorder(_borderButtonNormal);
        btnDelete.setMaximumSize(buttonSize);
        btnDelete.setMinimumSize(buttonSize);
        btnDelete.setPreferredSize(buttonSize);
        btnDelete.setToolTipText("Delete");
        btnDelete.setFocusPainted(false);
        btnDelete.setIcon(_image4);
        btnDelete.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button_mouseEntered(e);
            }
            public void mouseExited(MouseEvent e) {
                button_mouseExited(e);
            }
            public void mousePressed(MouseEvent e) {
                button_mousePressed(e);
            }
            public void mouseReleased(MouseEvent e) {
                button_mouseReleased(e);
            }
        });
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                button_actionPerformed(btnDelete, TOOL_DELETE);
            }
        });
        btnZoomIn.setBorder(_borderButtonNormal);
        btnZoomIn.setMaximumSize(buttonSize);
        btnZoomIn.setMinimumSize(buttonSize);
        btnZoomIn.setPreferredSize(buttonSize);
        btnZoomIn.setToolTipText("Zoom In");
        btnZoomIn.setFocusPainted(false);
        btnZoomIn.setIcon(_image5);
        btnZoomIn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button_mouseEntered(e);
            }
            public void mouseExited(MouseEvent e) {
                button_mouseExited(e);
            }
            public void mousePressed(MouseEvent e) {
                button_mousePressed(e);
            }
            public void mouseReleased(MouseEvent e) {
                button_mouseReleased(e);
            }
        });
        btnZoomIn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnZoomIn_actionPerformed(e);
            }
        });
        btnZoomOut.setBorder(_borderButtonNormal);
        btnZoomOut.setMaximumSize(buttonSize);
        btnZoomOut.setMinimumSize(buttonSize);
        btnZoomOut.setPreferredSize(buttonSize);
        btnZoomOut.setToolTipText("Zoom Out");
        btnZoomOut.setFocusPainted(false);
        btnZoomOut.setIcon(_image6);
        btnZoomOut.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button_mouseEntered(e);
            }
            public void mouseExited(MouseEvent e) {
                button_mouseExited(e);
            }
            public void mousePressed(MouseEvent e) {
                button_mousePressed(e);
            }
            public void mouseReleased(MouseEvent e) {
                button_mouseReleased(e);
            }
        });
        btnZoomOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnZoomOut_actionPerformed(e);
            }
        });
        btnLevelUp.setBorder(_borderButtonNormal);
        btnLevelUp.setMaximumSize(buttonSize);
        btnLevelUp.setMinimumSize(buttonSize);
        btnLevelUp.setPreferredSize(buttonSize);
        btnLevelUp.setToolTipText("Next Level Up");
        btnLevelUp.setFocusPainted(false);
        btnLevelUp.setIcon(_image7);
        btnLevelUp.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button_mouseEntered(e);
            }
            public void mouseExited(MouseEvent e) {
                button_mouseExited(e);
            }
            public void mousePressed(MouseEvent e) {
                button_mousePressed(e);
            }
            public void mouseReleased(MouseEvent e) {
                button_mouseReleased(e);
            }
        });
        btnLevelUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnLevelUp_actionPerformed(e);
            }
        });
        btnLevelDown.setBorder(_borderButtonNormal);
        btnLevelDown.setMaximumSize(buttonSize);
        btnLevelDown.setMinimumSize(buttonSize);
        btnLevelDown.setPreferredSize(buttonSize);
        btnLevelDown.setToolTipText("Next Level Down");
        btnLevelDown.setFocusPainted(false);
        btnLevelDown.setIcon(_image8);
        btnLevelDown.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button_mouseEntered(e);
            }
            public void mouseExited(MouseEvent e) {
                button_mouseExited(e);
            }
            public void mousePressed(MouseEvent e) {
                button_mousePressed(e);
            }
            public void mouseReleased(MouseEvent e) {
                button_mouseReleased(e);
            }
        });
        btnLevelDown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnLevelDown_actionPerformed(e);
            }
        });
        btnHelp.setBorder(_borderButtonNormal);
        btnHelp.setMaximumSize(buttonSize);
        btnHelp.setMinimumSize(buttonSize);
        btnHelp.setPreferredSize(buttonSize);
        btnHelp.setToolTipText("Help");
        btnHelp.setFocusPainted(false);
        btnHelp.setIcon(_image9);
        btnHelp.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button_mouseEntered(e);
            }
            public void mouseExited(MouseEvent e) {
                button_mouseExited(e);
            }
            public void mousePressed(MouseEvent e) {
                button_mousePressed(e);
            }
            public void mouseReleased(MouseEvent e) {
                button_mouseReleased(e);
            }
        });

        // Other buttons
        btnItemAdd.setText("Add/Update");
        btnItemAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnItemAdd_actionPerformed(e);
            }
        });
        btnItemDelete.setText("Clear/Delete");
        btnItemDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnItemDelete_actionPerformed(e);
            }
        });
        btnSenseAdd.setText("Add/Update");
        btnSenseAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnSenseAdd_actionPerformed(e);
            }
        });
        btnSenseDelete.setText("Clear/Delete");
        btnSenseDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnSenseDelete_actionPerformed(e);
            }
        });
        btnObjectAdd.setText("Add/Update");
        btnObjectAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnObjectAdd_actionPerformed(e);
            }
        });
        btnObjectDelete.setText("Clear/Delete");
        btnObjectDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnObjectDelete_actionPerformed(e);
            }
        });
        btnFunctionAdd.setText("Add/Update");
        btnFunctionAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnFunctionAdd_actionPerformed(e);
            }
        });
        btnFunctionDelete.setText("Clear/Delete");
        btnFunctionDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnFunctionDelete_actionPerformed(e);
            }
        });
        btnGuardAdd.setText("Add/Update");
        btnGuardAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnGuardAdd_actionPerformed(e);
            }
        });
        btnGuardDelete.setText("Clear/Del");
        btnGuardDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnGuardDelete_actionPerformed(e);
            }
        });
        btnRoomColour.setPreferredSize(new Dimension(40, 40));
        btnRoomColour.setFocusPainted(false);
        btnRoomColour.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRoomColour_actionPerformed(e);
            }
        });

        // Other controls
        lstItems.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                lstItems_valueChanged(e);
            }
        });
        lstSenses.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                lstSenses_valueChanged(e);
            }
        });
        lstObjects.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                lstObjects_valueChanged(e);
            }
        });
        lstFunctions.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                lstFunctions_valueChanged(e);
            }
        });

        // Menus
        menuFile.setText("File");
        menuFile.setMnemonic('F');
        menuFile.addMenuListener(new MenuListener() {
            public void menuSelected(MenuEvent e) {
                menuFile_menuSelected(e);
            }
            public void menuDeselected(MenuEvent e) {
            }
            public void menuCanceled(MenuEvent e) {
            }
        });
        menuFileNew.setText("New");
        menuFileNew.setMnemonic('N');
        menuFileNew.setAccelerator(KeyStroke.getKeyStroke(78, KeyEvent.CTRL_MASK, true));
        menuFileNew.addActionListener(new ActionListener()  {
            public void actionPerformed(ActionEvent e) {
                menuFileNew_actionPerformed(e);
            }
        });
        menuFileOpen.setText("Open...");
        menuFileOpen.setMnemonic('O');
        menuFileOpen.setAccelerator(KeyStroke.getKeyStroke(79, KeyEvent.CTRL_MASK, true));
        menuFileOpen.addActionListener(new ActionListener()  {
            public void actionPerformed(ActionEvent e) {
                menuFileOpen_actionPerformed(e);
            }
        });
        menuFileSave.setText("Save");
        menuFileSave.setMnemonic('S');
        menuFileSave.setAccelerator(KeyStroke.getKeyStroke(83, KeyEvent.CTRL_MASK, true));
        menuFileSave.addActionListener(new ActionListener()  {
            public void actionPerformed(ActionEvent e) {
                menuFileSave_actionPerformed(e);
            }
        });
        menuFileSaveAs.setText("Save As...");
        menuFileSaveAs.setMnemonic('V');
        menuFileSaveAs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuFileSaveAs_actionPerformed(e);
            }
        });
        menuFileExport.setText("Export to LPC...");
        menuFileExport.setMnemonic('E');
        menuFileExport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuFileExport_actionPerformed(e);
            }
        });
        menuFileExit.setText("Exit");
        menuFileExit.setMnemonic('X');
        menuFileExit.setAccelerator(KeyStroke.getKeyStroke(115, KeyEvent.ALT_MASK, true));
        menuFileExit.addActionListener(new ActionListener()  {
            public void actionPerformed(ActionEvent e) {
                menuFileExit_actionPerformed(e);
            }
        });
        menuEdit.setText("Edit");
        menuEdit.setMnemonic('E');
        menuEditCopy.setText("Copy");
        menuEditCopy.setMnemonic('C');
        menuEditCopy.setAccelerator(KeyStroke.getKeyStroke(67, KeyEvent.ALT_MASK, true));
        menuEditCopy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuEditCopy_actionPerformed(e);
            }
        });
        menuEditPaste.setText("Paste");
        menuEditPaste.setMnemonic('P');
        menuEditPaste.setAccelerator(KeyStroke.getKeyStroke(86, KeyEvent.ALT_MASK, true));
        menuEditPaste.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuEditPaste_actionPerformed(e);
            }
        });
        menuActions.setText("Actions");
        menuActions.setMnemonic('A');
        menuActionSetPath.setText("Set Map\'s Path...");
        menuActionSetPath.setMnemonic('S');
        menuActionSetPath.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuActionSetPath_actionPerformed(e);
            }
        });
        menuTools.setText("Tools");
        menuTools.setMnemonic('T');
        menuTools.addMenuListener(new MenuListener() {
            public void menuSelected(MenuEvent e) {
                menuTools_menuSelected(e);
            }
            public void menuDeselected(MenuEvent e) {
            }
            public void menuCanceled(MenuEvent e) {
            }
        });
        menuToolSelect.setText("Select");
        menuToolSelect.setMnemonic('S');
        menuToolSelect.setAccelerator(KeyStroke.getKeyStroke(83, KeyEvent.ALT_MASK, true));
        menuToolSelect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuToolSelect_actionPerformed(e);
            }
        });
        menuToolDelete.setText("Delete");
        menuToolDelete.setMnemonic('D');
        menuToolDelete.setAccelerator(KeyStroke.getKeyStroke(68, KeyEvent.ALT_MASK, false));
        menuToolDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuToolDelete_actionPerformed(e);
            }
        });
        menuToolAddRoom.setText("Add Room");
        menuToolAddRoom.setMnemonic('R');
        menuToolAddRoom.setAccelerator(KeyStroke.getKeyStroke(82, KeyEvent.ALT_MASK, false));
        menuToolAddRoom.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuToolAddRoo_actionPerformed(e);
            }
        });
        menuToolAddExit.setText("Add Exit");
        menuToolAddExit.setMnemonic('E');
        menuToolAddExit.setAccelerator(KeyStroke.getKeyStroke(69, KeyEvent.ALT_MASK, true));
        menuToolAddExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuToolAddExit_actionPerformed(e);
            }
        });
        menuToolLevelExitMenu.setText("Level Exit");
        menuToolLevelExitMenu.addMenuListener(new MenuListener() {
            public void menuSelected(MenuEvent e) {
                menuToolLevelExitMenu_menuSelected(e);
            }
            public void menuDeselected(MenuEvent e) {
            }
            public void menuCanceled(MenuEvent e) {
            }
        });
        menuLevelAddUp.setText("Add Up Exit");
        menuLevelAddUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuLevelAddUp_actionPerformed(e);
            }
        });
        menuLevelAddDown.setText("Add Down Exit");
        menuLevelAddDown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuLevelAddDown_actionPerformed(e);
            }
        });
        menuLevelSelectUp.setText("Select Up Exit");
        menuLevelSelectUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuLevelSelectUp_actionPerformed(e);
            }
        });
        menuLevelSelectDown.setText("Select Down Exit");
        menuLevelSelectDown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuLevelSelectDown_actionPerformed(e);
            }
        });
        menuLevelDeleteUp.setText("Delete Up Exit");
        menuLevelDeleteUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuLevelDeleteUp_actionPerformed(e);
            }
        });
        menuLevelDeleteDown.setText("Delete Down Exit");
        menuLevelDeleteDown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuLevelDeleteDown_actionPerformed(e);
            }
        });
        menuView.setText("View");
        menuView.setMnemonic('V');
        menuViewLower.setText("Lower Level");
        menuViewLower.setMnemonic('L');
        menuViewLower.setSelected(true);
        menuViewLower.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuViewLower_actionPerformed(e);
            }
        });
        menuViewUpper.setText("Upper Level");
        menuViewUpper.setMnemonic('U');
        menuViewUpper.setSelected(true);
        menuViewUpper.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuViewUpper_actionPerformed(e);
            }
        });
        menuViewProperties.setText("Properties");
        menuViewProperties.setMnemonic('P');
        menuViewProperties.setSelected(true);
        menuViewProperties.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuViewProperties_actionPerformed(e);
            }
        });
        menuLaF.setText("Look & Feel");
        menuLaF.setMnemonic('L');
        menuLaFJava.setText("Java");
        menuLaFJava.setMnemonic('J');
        menuSetup(menuLaFJava, _metal);
        menuLaFMac.setText("Macintosh");
        menuLaFMac.setMnemonic('M');
        menuSetup(menuLaFMac, _mac);
        menuLaFMotif.setText("Motif");
        menuLaFMotif.setMnemonic('F');
        menuSetup(menuLaFMotif, _motif);
        menuLaFWindows.setText("Windows");
        menuLaFWindows.setMnemonic('W');
        menuSetup(menuLaFWindows, _windows);
        menuHelp.setText("Help");
        menuHelp.setMnemonic('H');
        menuHelpAbout.setText("About");
        menuHelpAbout.setMnemonic('A');
        menuHelpAbout.addActionListener(new ActionListener()  {
            public void actionPerformed(ActionEvent e) {
                menuHelpAbout_actionPerformed(e);
            }
        });

        popupMenu.addPopupMenuListener(new PopupMenuListener() {
            public void popupMenuCanceled(PopupMenuEvent e) {
            }
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                popupMenu_popupMenuCanceled(e);
            }
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            }
        });
        addLevelExitItems(popupMenu);

        menuToolOptions.setMnemonic('O');
        menuToolOptions.setText("Options...");
        menuToolOptions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuToolOptions_actionPerformed(e);
            }
        });
        // --------------------------------------------------------------------------------
        // remove below, this is where Jbuilder likes to put the stuff from the designer...
        // --------------------------------------------------------------------------------

        menuFile.add(menuFileNew);
        menuFile.add(menuFileOpen);
        menuFile.add(menuFileSave);
        menuFile.add(menuFileSaveAs);
        menuFile.addSeparator();
        menuFile.add(menuFileExport);
        menuFile.addSeparator();
        menuFile.add(menuFileExit);
        menuEdit.add(menuEditCopy);
        menuEdit.add(menuEditPaste);
        menuActions.add(menuActionSetPath);
        menuTools.add(menuToolSelect);
        menuTools.add(menuToolDelete);
        menuTools.addSeparator();
        menuTools.add(menuToolAddRoom);
        menuTools.add(menuToolAddExit);
        menuTools.add(menuToolLevelExitMenu);
        menuTools.addSeparator();
        menuTools.add(menuToolOptions);
        menuView.add(menuViewUpper);
        menuView.add(menuViewLower);
        menuView.addSeparator();
        menuView.add(menuViewProperties);
        menuLaF.add(menuLaFJava);
        menuLaF.add(menuLaFMac);
        menuLaF.add(menuLaFMotif);
        menuLaF.add(menuLaFWindows);
        menuHelp.add(menuHelpAbout);
        myMenuBar.add(menuFile);
        myMenuBar.add(menuEdit);
        myMenuBar.add(menuActions);
        myMenuBar.add(menuTools);
        myMenuBar.add(menuView);
        myMenuBar.add(menuLaF);
        myMenuBar.add(menuHelp);
        setJMenuBar(myMenuBar);

        // Other Listeners
        optNotInvis.setText("This exit is NOT invisible");
        optNotInvis.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                optNotInvis_actionPerformed(e);
            }
        });
        optInvis.setText("This exit is invisible");
        optInvis.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                optInvis_actionPerformed(e);
            }
        });
        chkInvisibleRoom1.setText("Invisible from Room A");
        chkInvisibleRoom1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chkInvisibleRoom1_actionPerformed(e);
            }
        });
        chkInvisibleRoom2.setText("Invisible from Room B");
        chkInvisibleRoom2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chkInvisibleRoom2_actionPerformed(e);
            }
        });
        optNotDoor.setText("This exit is NOT a door");
        optNotDoor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                optNotDoor_actionPerformed(e);
            }
        });
        optDoor.setText("This exit is a door");
        optDoor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                optDoor_actionPerformed(e);
            }
        });
        optNotLockable.setText("This door is NOT Lockable");
        optNotLockable.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                optNotLockable_actionPerformed(e);
            }
        });
        optLockable.setText("This door is Lockable");
        optLockable.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                optLockable_actionPerformed(e);
            }
        });
        optIndoors.setText("Indoors");
        optIndoors.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                optIndoors_actionPerformed(e);
            }
        });
        optOutdoors.setText("Outdoors");
        optOutdoors.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                optOutdoors_actionPerformed(e);
            }
        });
        optSmells.setText("Smells");
        optSmells.setSelected(true);
        optSmells.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                optSmells_itemStateChanged(e);
            }
        });
        optSounds.setText("Sounds");
        optSounds.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                optSounds_itemStateChanged(e);
            }
        });
        chkSameDoorName.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chkSameDoorName_actionPerformed(e);
            }
        });
        chkSameDoorDesc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chkSameDoorDesc_actionPerformed(e);
            }
        });
        chkSameKeys.setText("Use same Key ID for both rooms");
        chkSameKeys.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chkSameKeys_actionPerformed(e);
            }
        });

        // Toolbars
        toolBar1.add(btnSelect);
        toolBar1.add(btnAddRoom);
        toolBar1.add(btnAddExit);
        toolBar1.add(btnDelete);
        toolBar1.add(separator1);
        toolBar1.add(btnZoomIn);
        toolBar1.add(btnZoomOut);
        toolBar1.add(separator2);
        toolBar1.add(btnLevelUp);
        toolBar1.add(btnLevelDown);
        toolBar1.add(separator3);
        toolBar1.add(btnHelp);

        // Misc
        pnlExitMiniMap.setBorder(BorderFactory.createLoweredBevelBorder());
        statusBar.setText("Ready");
        toolBar1.setBorder(null);
        toolBar1.setBorderPainted(false);
        separator1.setOrientation(SwingConstants.VERTICAL);
        separator1.setPreferredSize(new Dimension(3, 22));
        separator1.setMaximumSize(new Dimension(3, 22));
        separator2.setOrientation(SwingConstants.VERTICAL);
        separator2.setPreferredSize(new Dimension(3, 22));
        separator2.setMaximumSize(new Dimension(3, 22));
        separator3.setOrientation(SwingConstants.VERTICAL);
        jLabel1.setText("Room Number ");
        jLabel2.setText("Short Description");
        jLabel3.setText("Long Description");
        jLabel4.setText("Room Name");
        jLabel26.setText("Description for code comment");
        txtRoomLongDesc.setWrapStyleWord(true);
        txtRoomLongDesc.setLineWrap(true);
        txtRoomLongDesc.setFont(textFont);
        txtRoomLongDescCopy.setFont(new Font("SansSerif", 0, 12));
        txtRoomLongDescCopy.setDisabledTextColor(Color.black);
        txtRoomLongDescCopy.setLineWrap(true);
        txtRoomLongDescCopy.setEnabled(false);
        txtRoomLongDescCopy.setWrapStyleWord(true);
        txtRoomLongDescCopy.setDocument(txtRoomLongDesc.getDocument());
        txtRoomLongDescCopy.setBackground(EleConstants.DISABLED_COLOUR);
        jScrollPane8.setBorder(BorderFactory.createLoweredBevelBorder());
        jScrollPane8.setMinimumSize(new Dimension(0, 55));
        jScrollPane8.setPreferredSize(new Dimension(0, 55));
        jLabel27.setText("Copy of Long Description");
        txtRoomName.setMinimumSize(new Dimension(120, 21));
        txtRoomName.setPreferredSize(new Dimension(120, 21));
        pnlProperties.setMinimumSize(new Dimension(500, 300));
        pnlProperties.setPreferredSize(new Dimension(500, 300));
        jLabel5.setText("Key ID from Room A");
        jLabel6.setText("Room A is Room Number ");
        jLabel7.setText("Room B is Room Number ");
        jLabel8.setText("Items currently in room");
        jLabel9.setText("From Room A");
        jLabel10.setText("From Room B");
        buttonGroupExitInvis.add(optNotInvis);
        buttonGroupExitInvis.add(optInvis);
        buttonGroupExitDoor.add(optNotDoor);
        buttonGroupExitDoor.add(optDoor);
        buttonGroupExitLock.add(optNotLockable);
        buttonGroupExitLock.add(optLockable);
        buttonGroupRoomOutDoors.add(optIndoors);
        buttonGroupRoomOutDoors.add(optOutdoors);
        buttonGroupRoomSense.add(optSmells);
        buttonGroupRoomSense.add(optSounds);
        buttonGroupRoomObjectType.add(optObjectTypeMon);
        buttonGroupRoomObjectType.add(optObjectTypeObj);
        buttonGroupRoomObjectLoadType.add(optObjectLoadUnique);
        buttonGroupRoomObjectLoadType.add(optObjectLoadPresent);
        buttonGroupRoomObjectLoadType.add(optObjectLoadTrack);
        buttonGroupGuardCheck.add(optGuardCheckDefault);
        buttonGroupGuardCheck.add(optGuardCheckFunction);
        buttonGroupGuardCheck.add(optGuardCheckInline);
        buttonGroupGuardCheck.add(optGuardCheckGPRESENT);
        buttonGroupGuardMessage.add(optGuardMessageDefault);
        buttonGroupGuardMessage.add(optGuardMessageGMSG);
        buttonGroupGuardMessage.add(optGuardMessageFunction);
        buttonGroupGuardMessage.add(optGuardMessageInline);
        buttonGroupGuardWizard.add(optGuardWizardDefault);
        buttonGroupGuardWizard.add(optGuardWizardFunction);
        buttonGroupGuardWizard.add(optGuardWizardInline);
        lblExitDirection[EleUtils.translateDirectionToIndex(EleConstants.DIRECTION_NORTH)].setHorizontalAlignment(SwingConstants.CENTER);
        lblExitDirection[EleUtils.translateDirectionToIndex(EleConstants.DIRECTION_SOUTH)].setHorizontalAlignment(SwingConstants.CENTER);
        pnlExitMiniMap.setBackground(EleConstants.BACKGROUND_COLOUR);
        lstItems.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        txtItemDescription.setWrapStyleWord(true);
        txtItemDescription.setLineWrap(true);
        txtItemDescription.setBorder(null);
        txtItemDescription.setFont(textFont);
        jLabel11.setText("Item(s) (Comma seperated)");
        jLabel12.setText("Description");
        jScrollPane1.setBorder(BorderFactory.createLoweredBevelBorder());
        jScrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setBorder(BorderFactory.createLoweredBevelBorder());
        jScrollPane2.setMinimumSize(new Dimension(150, 0));
        jScrollPane2.setPreferredSize(new Dimension(150, 0));
        jScrollPane3.setBorder(BorderFactory.createLoweredBevelBorder());
        jLabel13.setText("This room is...");
        jLabel14.setText("Terrains (max "+ Room.MAX_TERRAINS+")");
        jLabel15.setText("Light Level");
        jScrollPane4.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane4.setBorder(BorderFactory.createLoweredBevelBorder());
        jScrollPane4.setMinimumSize(new Dimension(150, 0));
        jScrollPane4.setPreferredSize(new Dimension(150, 0));
        sldrLightLevel.setMinorTickSpacing(1);
        sldrLightLevel.setMajorTickSpacing(2);
        sldrLightLevel.setPaintLabels(true);
        sldrLightLevel.setMinimum(-4);
        sldrLightLevel.setValue(3);
        sldrLightLevel.setPaintTicks(true);
        sldrLightLevel.setMaximum(10);
        sldrLightLevel.setSnapToTicks(true);
        jLabel16.setText("Default (will have to be invoked)");
        jLabel17.setText("Default (will appear in the room)");
        jLabel18.setText("What do you want to sense");
        jLabel19.setText("Description");
        jLabel20.setText("Currently in room");
        jLabel21.setText("Currently in room");
        jLabel22.setText("File Name");
        jLabel23.setText("Message to room when Object is loaded");
        txtSenseDescription.setWrapStyleWord(true);
        txtSenseDescription.setLineWrap(true);
        txtSenseDescription.setFont(textFont);
        jScrollPane5.setBorder(BorderFactory.createLoweredBevelBorder());
        jScrollPane6.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane6.setBorder(BorderFactory.createLoweredBevelBorder());
        jScrollPane6.setMinimumSize(new Dimension(150, 0));
        jScrollPane6.setPreferredSize(new Dimension(150, 0));
        jScrollPane7.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane7.setBorder(BorderFactory.createLoweredBevelBorder());
        jScrollPane7.setMinimumSize(new Dimension(150, 0));
        jScrollPane7.setPreferredSize(new Dimension(150, 0));
        jScrollPane10.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane10.setBorder(BorderFactory.createLoweredBevelBorder());
        jScrollPane10.setMinimumSize(new Dimension(150, 0));
        jScrollPane10.setPreferredSize(new Dimension(150, 0));
        jScrollPane12.setBorder(BorderFactory.createLoweredBevelBorder());
        jScrollPane12.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane12.setMinimumSize(new Dimension(150, 0));
        jScrollPane12.setPreferredSize(new Dimension(150, 0));
        jScrollPane14.setBorder(BorderFactory.createLoweredBevelBorder());
        jScrollPane14.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane14.setMinimumSize(new Dimension(150, 0));
        jScrollPane14.setPreferredSize(new Dimension(150, 0));
        lstSenses.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lstObjects.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lstAvailableGuardObjects.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lstGuards.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        optObjectTypeObj.setText("Object");
        optObjectTypeMon.setText("Monster");
        optObjectLoadUnique.setText("Only ever load one (Unique)");
        optObjectLoadPresent.setText("Load if object is not present");
        optObjectLoadTrack.setText("Load when previous one is destroyed");
        jLabel28.setText("Key ID from Room B");
        jLabel25.setText("Boundary");
        jLabel29.setText("Extra code - anything entered here will be placed in the file as code");
        jLabel24.setText("Room Colour");
        jLabel35.setText("Objects available");
        jLabel30.setText("Directions to guard");
        jLabel36.setText("Name");
        jLabel37.setText("Use same");
        jLabel39.setText("Door description");
        jLabel38.setText("Concealed");
        jLabel40.setText("Open description");
        jLabel41.setText("Closed description");
        jLabel31.setVerifyInputWhenFocusTarget(true);
        jLabel31.setText("Check that object makes");
        jLabel31.setVerticalAlignment(SwingConstants.TOP);
        jLabel31.setVerticalTextPosition(SwingConstants.TOP);
        jLabel32.setText("Room msg");
        jLabel33.setText("Messages");
        jLabel34.setText("Wizard message");
        jScrollPane11.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane11.setBorder(BorderFactory.createLoweredBevelBorder());
        jScrollPane11.setPreferredSize(new Dimension(100, 110));
        optGuardCheckDefault.setMargin(optionInsets);
        optGuardCheckDefault.setSelected(true);
        optGuardMessageDefault.setSelected(true);
        optGuardWizardDefault.setSelected(true);
        optGuardCheckDefault.setText("Default");
        optGuardCheckGPRESENT.setMargin(optionInsets);
        optGuardCheckGPRESENT.setText("G_PRESENT");
        optGuardCheckFunction.setMargin(optionInsets);
        optGuardCheckFunction.setText("Function");
        optGuardCheckInline.setMargin(optionInsets);
        optGuardCheckInline.setText("Inline");
        txtGuardCheckInline.setFont(codeFont);
        txtGuardCheckInline.setPreferredSize(new Dimension(100, 21));
        selGuardCheckFunctions.setPreferredSize(new Dimension(100, 21));
        txtGuardMessageInline.setFont(codeFont);
        txtGuardMessageInline.setPreferredSize(new Dimension(100, 21));
        selGuardMessageFunctions.setPreferredSize(new Dimension(100, 21));
        txtGuardWizardInline.setFont(codeFont);
        txtGuardWizardInline.setPreferredSize(new Dimension(100, 21));
        selGuardWizardFunctions.setPreferredSize(new Dimension(100, 21));
        txtGuardMessageRoom.setPreferredSize(new Dimension(100, 21));
        txtGuardMessagePlayer.setPreferredSize(new Dimension(100, 21));
        optGuardMessageInline.setMargin(optionInsets);
        optGuardMessageInline.setText("Inline");
        optGuardMessageFunction.setMargin(optionInsets);
        optGuardMessageFunction.setText("Function");
        optGuardMessageDefault.setMargin(optionInsets);
        optGuardMessageDefault.setText("Default");
        optGuardMessageGMSG.setMargin(optionInsets);
        optGuardMessageGMSG.setText("Player msg");
        optGuardWizardDefault.setMargin(optionInsets);
        optGuardWizardDefault.setText("Default");
        optGuardWizardFunction.setMargin(optionInsets);
        optGuardWizardFunction.setText("Function    ");
        optGuardWizardInline.setMargin(optionInsets);
        optGuardWizardInline.setText("Inline");
        jLabel42.setText("Functions");
        lstFunctions.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jLabel43.setText("Body (Do not include the curly braces)");
        jLabel44.setText("Arguments (Do not enclose in braces)");
        jLabel45.setText("Name");
        jLabel46.setText("Return type");
        jLabel47.setText("Guards");

        // Layout stuff
        // Main stuff
        pnlStatusLayout.setLayout(borderLayoutStatus);
        pnlStatusLayout.add(statusBar, BorderLayout.WEST);
        pnlStatusLayout.add(statusBarLevel, BorderLayout.EAST);

        pnlProperties.setLayout(borderLayout8);
        pnlPropertiesLayout.setLayout(borderLayoutProperties);
        pnlPropertiesLayout.add(pnlProperties, BorderLayout.CENTER);
        pnlPropertiesLayout.add(pnlStatusLayout, BorderLayout.SOUTH);

        pnlScrollMapLayout.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pnlScrollMapLayout.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        pnlScrollMapLayout.getViewport().add(pnlMap, null);
        pnlContent.setLayout(borderLayout7);
        pnlContent.add(toolBar1, BorderLayout.NORTH);
        pnlContent.add(pnlPropertiesLayout, BorderLayout.SOUTH);
        pnlContent.add(pnlScrollMapLayout, BorderLayout.CENTER);

        // Room tabbed pane
        // Basics
        pnlRoomBasicsLayout1.setLayout(borderLayout5);
        pnlRoomBasicsLayout1.add(lblRoomNumber, BorderLayout.CENTER);
        pnlRoomBasicsLayout1.add(jLabel1, BorderLayout.WEST);
        pnlRoomBasicsLayout2.setLayout(borderLayout1);
        pnlRoomBasicsLayout2.add(jLabel26, BorderLayout.NORTH);
        pnlRoomBasicsLayout2.add(txtRoomCodeDesc, BorderLayout.SOUTH);
        pnlRoomBasicsLayout3.setLayout(borderLayout2);
        pnlRoomBasicsLayout3.add(jLabel4, BorderLayout.NORTH);
        pnlRoomBasicsLayout3.add(txtRoomName, BorderLayout.SOUTH);
        pnlRoomBasicsLayout4.setBorder(_borderInset5);
        pnlRoomBasicsLayout4.setLayout(borderLayout3);
        pnlRoomBasicsLayout4.add(jLabel3, BorderLayout.NORTH);
        pnlRoomBasicsLayout4.add(jScrollPane1, BorderLayout.CENTER);
        jScrollPane1.getViewport().add(txtRoomLongDesc, null);
        pnlRoomBasicsLayout5.setLayout(borderLayout4);
        pnlRoomBasicsLayout5.add(pnlRoomBasicsLayout2, BorderLayout.CENTER);
        pnlRoomBasicsLayout5.add(pnlRoomBasicsLayout3, BorderLayout.EAST);
        pnlRoomBasicsLayout7.setLayout(borderLayout47);
        pnlRoomBasicsLayout7.add(jLabel2, BorderLayout.NORTH);
        pnlRoomBasicsLayout7.add(txtRoomShortDesc, BorderLayout.SOUTH);
        pnlRoomBasicsLayout6.setLayout(verticalFlowLayout1);
        pnlRoomBasicsLayout6.add(pnlRoomBasicsLayout1, null);
        pnlRoomBasicsLayout6.add(pnlRoomBasicsLayout5, null);
        pnlRoomBasicsLayout6.add(pnlRoomBasicsLayout7, null);
        pnlRoomBasics.setLayout(borderLayout6);
        pnlRoomBasics.add(pnlRoomBasicsLayout4, BorderLayout.CENTER);
        pnlRoomBasics.add(pnlRoomBasicsLayout6, BorderLayout.NORTH);
        // Items
        jScrollPane2.getViewport().add(lstItems, null);
        pnlRoomItemsLayout1.setLayout(borderLayout12);
        pnlRoomItemsLayout1.setBorder(_borderInset5);
        pnlRoomItemsLayout1.add(jLabel8, BorderLayout.NORTH);
        pnlRoomItemsLayout1.add(jScrollPane2, BorderLayout.WEST);
        pnlRoomItemsLayout4.add(btnItemAdd, null);
        pnlRoomItemsLayout4.add(btnItemDelete, null);
        pnlRoomItemsLayout5.setLayout(borderLayout17);
        pnlRoomItemsLayout5.add(jLabel11, BorderLayout.NORTH);
        pnlRoomItemsLayout5.add(txtItemNames, BorderLayout.CENTER);
        pnlRoomItemsLayout5.add(jLabel12, BorderLayout.SOUTH);
        pnlRoomItemsLayout3.setLayout(borderLayout20);
        pnlRoomItemsLayout3.add(pnlRoomItemsLayout4, BorderLayout.NORTH);
        pnlRoomItemsLayout3.add(pnlRoomItemsLayout5, BorderLayout.SOUTH);
        jScrollPane3.getViewport().add(txtItemDescription, null);
        pnlRoomItemsLayout2.setLayout(borderLayout18);
        pnlRoomItemsLayout2.setBorder(_borderInset5);
        pnlRoomItemsLayout2.add(pnlRoomItemsLayout3, BorderLayout.NORTH);
        pnlRoomItemsLayout2.add(jScrollPane3, BorderLayout.CENTER);
        jScrollPane8.getViewport().add(txtRoomLongDescCopy, null);
        pnlRoomItemsLayout6.setLayout(borderLayout48);
        pnlRoomItemsLayout6.setBorder(_borderInset5);
        pnlRoomItemsLayout6.add(jLabel27, BorderLayout.NORTH);
        pnlRoomItemsLayout6.add(jScrollPane8, BorderLayout.CENTER);
        pnlRoomItems.setLayout(borderLayout19);
        pnlRoomItems.add(pnlRoomItemsLayout1, BorderLayout.WEST);
        pnlRoomItems.add(pnlRoomItemsLayout2, BorderLayout.CENTER);
        pnlRoomItems.add(pnlRoomItemsLayout6, BorderLayout.SOUTH);
        // Misc
        pnlRoomMiscLayout3.setLayout(borderLayout21);
        pnlRoomMiscLayout3.add(optIndoors, BorderLayout.WEST);
        pnlRoomMiscLayout3.add(optOutdoors, BorderLayout.EAST);
        pnlRoomMiscLayout3.add(jLabel13, BorderLayout.NORTH);
        jScrollPane4.getViewport().add(lstTerrains, null);
        pnlRoomMiscLayout4.setLayout(borderLayout22);
        pnlRoomMiscLayout4.add(jLabel14, BorderLayout.NORTH);
        pnlRoomMiscLayout4.add(jScrollPane4, BorderLayout.WEST);
        pnlRoomMiscLayout1.setBorder(_borderInset5);
        pnlRoomMiscLayout1.setLayout(borderLayout23);
        pnlRoomMiscLayout1.add(pnlRoomMiscLayout3, BorderLayout.NORTH);
        pnlRoomMiscLayout1.add(pnlRoomMiscLayout4, BorderLayout.CENTER);
        pnlRoomMiscLayout5.setLayout(borderLayout25);
        pnlRoomMiscLayout5.add(sldrLightLevel, BorderLayout.WEST);
        pnlRoomMiscLayout5.add(jLabel15, BorderLayout.NORTH);
        pnlRoomMiscLayout2.setBorder(_borderInset5);
        pnlRoomMiscLayout2.setLayout(borderLayout26);
        pnlRoomMiscLayout2.add(pnlRoomMiscLayout5, BorderLayout.NORTH);
        pnlRoomMisc.setLayout(borderLayout24);
        pnlRoomMisc.add(pnlRoomMiscLayout1, BorderLayout.WEST);
        pnlRoomMisc.add(pnlRoomMiscLayout2, BorderLayout.EAST);
        // Senses
        pnlRoomSensesLayout10.setLayout(verticalFlowLayout4);
        pnlRoomSensesLayout10.add(btnSenseAdd, null);
        pnlRoomSensesLayout10.add(btnSenseDelete, null);
        pnlRoomSensesLayout11.setLayout(borderLayout36);
        pnlRoomSensesLayout11.add(jLabel18, BorderLayout.NORTH);
        pnlRoomSensesLayout11.add(txtSenseName, BorderLayout.SOUTH);
        pnlRoomSensesLayout6.add(optSmells, null);
        pnlRoomSensesLayout6.add(optSounds, null);
        pnlRoomSensesLayout7.setLayout(borderLayout30);
        pnlRoomSensesLayout7.add(jLabel19, BorderLayout.SOUTH);
        pnlRoomSensesLayout7.add(pnlRoomSensesLayout11, BorderLayout.CENTER);
        pnlRoomSensesLayout8.setLayout(borderLayout27);
        pnlRoomSensesLayout8.add(jLabel17, BorderLayout.NORTH);
        pnlRoomSensesLayout8.add(txtVisibleDefaultSense, BorderLayout.CENTER);
        pnlRoomSensesLayout9.setLayout(borderLayout28);
        pnlRoomSensesLayout9.add(jLabel16, BorderLayout.NORTH);
        pnlRoomSensesLayout9.add(txtDefaultSense, BorderLayout.CENTER);
        pnlRoomSensesLayout3.setLayout(borderLayout33);
        pnlRoomSensesLayout3.add(pnlRoomSensesLayout6, BorderLayout.NORTH);
        pnlRoomSensesLayout3.add(jLabel20, BorderLayout.SOUTH);
        pnlRoomSensesLayout4.setLayout(borderLayout29);
        pnlRoomSensesLayout4.add(pnlRoomSensesLayout9, BorderLayout.NORTH);
        pnlRoomSensesLayout4.add(pnlRoomSensesLayout8, BorderLayout.SOUTH);
        jScrollPane5.getViewport().add(txtSenseDescription, null);
        pnlRoomSensesLayout5.setLayout(borderLayout31);
        pnlRoomSensesLayout5.add(pnlRoomSensesLayout7, BorderLayout.NORTH);
        pnlRoomSensesLayout5.add(pnlRoomSensesLayout10, BorderLayout.EAST);
        pnlRoomSensesLayout5.add(jScrollPane5, BorderLayout.CENTER);
        jScrollPane6.getViewport().add(lstSenses, null);
        pnlRoomSensesLayout1.setBorder(_borderInset5);
        pnlRoomSensesLayout1.setLayout(borderLayout34);
        pnlRoomSensesLayout1.add(pnlRoomSensesLayout3, BorderLayout.NORTH);
        pnlRoomSensesLayout1.add(jScrollPane6, BorderLayout.CENTER);
        pnlRoomSensesLayout2.setBorder(_borderInset5);
        pnlRoomSensesLayout2.setLayout(borderLayout32);
        pnlRoomSensesLayout2.add(pnlRoomSensesLayout5, BorderLayout.CENTER);
        pnlRoomSensesLayout2.add(pnlRoomSensesLayout4, BorderLayout.NORTH);
        pnlRoomSenses.setLayout(borderLayout35);
        pnlRoomSenses.add(pnlRoomSensesLayout1, BorderLayout.WEST);
        pnlRoomSenses.add(pnlRoomSensesLayout2, BorderLayout.CENTER);
        // Objects
        pnlRoomObjectsLayout9.add(optObjectTypeMon, null);
        pnlRoomObjectsLayout9.add(optObjectTypeObj, null);
        pnlRoomObjectsLayout10.setLayout(borderLayout38);
        pnlRoomObjectsLayout10.add(txtObjectFileName, BorderLayout.SOUTH);
        pnlRoomObjectsLayout10.add(jLabel22, BorderLayout.NORTH);
        pnlRoomObjectsLayout3.setLayout(borderLayout39);
        pnlRoomObjectsLayout3.add(pnlRoomObjectsLayout9, BorderLayout.NORTH);
        pnlRoomObjectsLayout3.add(pnlRoomObjectsLayout10, BorderLayout.SOUTH);
        pnlRoomObjectsLayout5.setLayout(borderLayout40);
        pnlRoomObjectsLayout5.add(txtObjectMessage, BorderLayout.SOUTH);
        pnlRoomObjectsLayout5.add(jLabel23, BorderLayout.NORTH);
        pnlRoomObjectsLayout6.setLayout(verticalFlowLayout2);
        pnlRoomObjectsLayout6.add(optObjectLoadTrack, null);
        pnlRoomObjectsLayout6.add(optObjectLoadPresent, null);
        pnlRoomObjectsLayout6.add(optObjectLoadUnique, null);
        pnlRoomObjectsLayout8.setLayout(verticalFlowLayout3);
        pnlRoomObjectsLayout8.add(btnObjectAdd, null);
        pnlRoomObjectsLayout8.add(btnObjectDelete, null);
        pnlRoomObjectsLayout7.add(pnlRoomObjectsLayout8, null);
        pnlRoomObjectsLayout4.setLayout(borderLayout41);
        pnlRoomObjectsLayout4.add(pnlRoomObjectsLayout5, BorderLayout.NORTH);
        pnlRoomObjectsLayout4.add(pnlRoomObjectsLayout6, BorderLayout.WEST);
        pnlRoomObjectsLayout4.add(pnlRoomObjectsLayout7, BorderLayout.EAST);
        jScrollPane7.getViewport().add(lstObjects, null);
        pnlRoomObjectsLayout1.setBorder(_borderInset5);
        pnlRoomObjectsLayout1.setLayout(borderLayout37);
        pnlRoomObjectsLayout1.add(jLabel21, BorderLayout.NORTH);
        pnlRoomObjectsLayout1.add(jScrollPane7, BorderLayout.CENTER);
        pnlRoomObjectsLayout2.setBorder(_borderInset5);
        pnlRoomObjectsLayout2.setLayout(borderLayout42);
        pnlRoomObjectsLayout2.add(pnlRoomObjectsLayout3, BorderLayout.NORTH);
        pnlRoomObjectsLayout2.add(pnlRoomObjectsLayout4, BorderLayout.SOUTH);
        pnlRoomObjects.setLayout(borderLayout43);
        pnlRoomObjects.add(pnlRoomObjectsLayout1, BorderLayout.WEST);
        pnlRoomObjects.add(pnlRoomObjectsLayout2, BorderLayout.CENTER);
        // Guards
        jScrollPane10.getViewport().add(lstAvailableGuardObjects, null);
        pnlRoomGuardsLayout18.setLayout(borderLayout71);
        pnlRoomGuardsLayout18.add(jScrollPane10, BorderLayout.CENTER);
        pnlRoomGuardsLayout18.add(jLabel35, BorderLayout.NORTH);
        jScrollPane14.getViewport().add(lstGuards, null);
        pnlRoomGuardsLayout17.setLayout(borderLayout61);
        pnlRoomGuardsLayout17.add(jScrollPane14, BorderLayout.CENTER);
        pnlRoomGuardsLayout17.add(jLabel47, BorderLayout.NORTH);
        pnlRoomGuardsLayout16.setLayout(gridBagLayout3);
        pnlRoomGuardsLayout16.add(optGuardWizardDefault, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, zeroInsets, 0, 0));
        pnlRoomGuardsLayout16.add(optGuardWizardFunction, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, zeroInsets, 0, 0));
        pnlRoomGuardsLayout16.add(selGuardWizardFunctions, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, zeroInsets, 0, 0));
        pnlRoomGuardsLayout16.add(optGuardWizardInline, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, zeroInsets, 0, 0));
        pnlRoomGuardsLayout16.add(txtGuardWizardInline, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, zeroInsets, 0, 0));
        pnlRoomGuardsLayout12.setLayout(gridBagLayout1);
        pnlRoomGuardsLayout12.add(optGuardMessageDefault, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, zeroInsets, 0, 0));
        pnlRoomGuardsLayout12.add(optGuardMessageGMSG, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, zeroInsets, 0, 0));
        pnlRoomGuardsLayout12.add(txtGuardMessagePlayer, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.NONE, zeroInsets, 0, 0));
        pnlRoomGuardsLayout12.add(jLabel32, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 8), 0, 0));
        pnlRoomGuardsLayout12.add(txtGuardMessageRoom, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.NONE, zeroInsets, 0, 0));
        pnlRoomGuardsLayout12.add(optGuardMessageFunction, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, zeroInsets, 0, 0));
        pnlRoomGuardsLayout12.add(selGuardMessageFunctions, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, zeroInsets, 0, 0));
        pnlRoomGuardsLayout12.add(optGuardMessageInline, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, zeroInsets, 0, 0));
        pnlRoomGuardsLayout12.add(txtGuardMessageInline, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, zeroInsets, 0, 0));
        pnlRoomGuardsLayout10.setLayout(gridBagLayout1);
        pnlRoomGuardsLayout10.add(optGuardCheckDefault, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, zeroInsets, 0, 0));
        pnlRoomGuardsLayout10.add(optGuardCheckGPRESENT, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, zeroInsets, 0, 0));
        pnlRoomGuardsLayout10.add(optGuardCheckFunction, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, zeroInsets, 0, 0));
        pnlRoomGuardsLayout10.add(selGuardCheckFunctions, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, zeroInsets, 0, 0));
        pnlRoomGuardsLayout10.add(optGuardCheckInline, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, zeroInsets, 0, 0));
        pnlRoomGuardsLayout10.add(txtGuardCheckInline, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, zeroInsets, 0, 0));
        pnlRoomGuardsLayout15.setPreferredSize(new Dimension(-1, 20));
        pnlRoomGuardsLayout15.add(jLabel34, null);
        pnlRoomGuardsLayout14.add(btnGuardAdd, null);
        pnlRoomGuardsLayout14.add(btnGuardDelete, null);
        pnlRoomGuardsLayout13.setBorder(BorderFactory.createEtchedBorder());
        pnlRoomGuardsLayout13.setLayout(borderLayout59);
        pnlRoomGuardsLayout13.add(pnlRoomGuardsLayout15, BorderLayout.NORTH);
        pnlRoomGuardsLayout13.add(pnlRoomGuardsLayout16, BorderLayout.SOUTH);
        pnlRoomGuardsLayout11.setPreferredSize(new Dimension(-1, 20));
        pnlRoomGuardsLayout11.add(jLabel33, null);
        pnlRoomGuardsLayout9.setPreferredSize(new Dimension(-1, 20));
        pnlRoomGuardsLayout9.add(jLabel31, null);
        pnlRoomGuardsLayout8.setLayout(borderLayout60);
        pnlRoomGuardsLayout8.add(pnlRoomGuardsLayout13, BorderLayout.NORTH);
        pnlRoomGuardsLayout8.add(pnlRoomGuardsLayout14, BorderLayout.SOUTH);
        pnlRoomGuardsLayout7.setBorder(BorderFactory.createEtchedBorder());
        pnlRoomGuardsLayout7.setLayout(borderLayout57);
        pnlRoomGuardsLayout7.add(pnlRoomGuardsLayout11, BorderLayout.NORTH);
        pnlRoomGuardsLayout7.add(pnlRoomGuardsLayout12, BorderLayout.SOUTH);
        pnlRoomGuardsLayout6.setBorder(BorderFactory.createEtchedBorder());
        pnlRoomGuardsLayout6.setLayout(borderLayout58);
        pnlRoomGuardsLayout6.add(pnlRoomGuardsLayout9, BorderLayout.NORTH);
        pnlRoomGuardsLayout6.add(pnlRoomGuardsLayout10, BorderLayout.SOUTH);
        jScrollPane11.getViewport().add(lstGuardDirections, null);
        pnlRoomGuardsLayout5.setLayout(borderLayout56);
        pnlRoomGuardsLayout5.add(jScrollPane11, BorderLayout.CENTER);
        pnlRoomGuardsLayout5.add(jLabel30, BorderLayout.NORTH);
        pnlRoomGuardsLayout4.setLayout(borderLayout55);
        pnlRoomGuardsLayout4.add(pnlRoomGuardsLayout7, BorderLayout.NORTH);
        pnlRoomGuardsLayout4.add(pnlRoomGuardsLayout8, BorderLayout.SOUTH);
        pnlRoomGuardsLayout3.setLayout(borderLayout54);
        pnlRoomGuardsLayout3.add(pnlRoomGuardsLayout5, BorderLayout.NORTH);
        pnlRoomGuardsLayout3.add(pnlRoomGuardsLayout6, BorderLayout.SOUTH);
        pnlRoomGuardsLayout2.setBorder(_borderInset5);
        pnlRoomGuardsLayout2.setLayout(borderLayout53);
        pnlRoomGuardsLayout2.add(pnlRoomGuardsLayout3, BorderLayout.WEST);
        pnlRoomGuardsLayout2.add(pnlRoomGuardsLayout4, BorderLayout.EAST);
        pnlRoomGuardsLayout1.setBorder(_borderInset5);
        pnlRoomGuardsLayout1.setLayout(gridBagLayout2);
        pnlRoomGuardsLayout1.add(pnlRoomGuardsLayout17,  new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH, zeroInsets, 0, 0));
        pnlRoomGuardsLayout1.add(pnlRoomGuardsLayout18,  new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH, zeroInsets, 0, 0));
        pnlRoomGuards.setLayout(borderLayout51);
        pnlRoomGuards.add(pnlRoomGuardsLayout1, BorderLayout.WEST);
        pnlRoomGuards.add(pnlRoomGuardsLayout2, BorderLayout.CENTER);
        // Functions
        pnlRoomFunctionsLayout10.setLayout(borderLayout68);
        pnlRoomFunctionsLayout10.add(jLabel44, BorderLayout.NORTH);
        pnlRoomFunctionsLayout10.add(txtFunctionArguments, BorderLayout.CENTER);
        pnlRoomFunctionsLayout9.setLayout(verticalFlowLayout6);
        pnlRoomFunctionsLayout9.add(btnFunctionAdd, null);
        pnlRoomFunctionsLayout9.add(btnFunctionDelete, null);
        pnlRoomFunctionsLayout8.setLayout(borderLayout67);
        pnlRoomFunctionsLayout8.add(pnlRoomFunctionsLayout10, BorderLayout.NORTH);
        pnlRoomFunctionsLayout8.add(jLabel43, BorderLayout.SOUTH);
        pnlRoomFunctionsLayout7.setLayout(borderLayout69);
        pnlRoomFunctionsLayout7.add(jLabel45, BorderLayout.NORTH);
        pnlRoomFunctionsLayout7.add(txtFunctionName, BorderLayout.SOUTH);
        pnlRoomFunctionsLayout6.setLayout(borderLayout70);
        pnlRoomFunctionsLayout6.add(jLabel46, BorderLayout.NORTH);
        pnlRoomFunctionsLayout6.add(txtFunctionReturnType, BorderLayout.CENTER);
        jScrollPane13.getViewport().add(txtFunctionBody, null);
        pnlRoomFunctionsLayout5.setLayout(borderLayout66);
        pnlRoomFunctionsLayout5.add(jScrollPane13, BorderLayout.CENTER);
        pnlRoomFunctionsLayout5.add(pnlRoomFunctionsLayout8, BorderLayout.NORTH);
        pnlRoomFunctionsLayout5.add(pnlRoomFunctionsLayout9, BorderLayout.EAST);
        pnlRoomFunctionsLayout4.setLayout(borderLayout65);
        pnlRoomFunctionsLayout4.add(pnlRoomFunctionsLayout6, BorderLayout.NORTH);
        pnlRoomFunctionsLayout4.add(pnlRoomFunctionsLayout7, BorderLayout.SOUTH);
        pnlRoomFunctionsLayout2.setBorder(_borderInset5);
        pnlRoomFunctionsLayout2.setLayout(borderLayout64);
        pnlRoomFunctionsLayout2.add(pnlRoomFunctionsLayout4, BorderLayout.NORTH);
        pnlRoomFunctionsLayout2.add(pnlRoomFunctionsLayout5, BorderLayout.CENTER);
        jScrollPane12.getViewport().add(lstFunctions, null);
        pnlRoomFunctionsLayout1.setBorder(_borderInset5);
        pnlRoomFunctionsLayout1.setLayout(borderLayout63);
        pnlRoomFunctionsLayout1.add(jLabel42, BorderLayout.NORTH);
        pnlRoomFunctionsLayout1.add(jScrollPane12, BorderLayout.CENTER);
        pnlRoomFunctions.setLayout(borderLayout62);
        pnlRoomFunctions.add(pnlRoomFunctionsLayout1, BorderLayout.WEST);
        pnlRoomFunctions.add(pnlRoomFunctionsLayout2, BorderLayout.CENTER);
        // Advanced
        pnlRoomAdvancedLayout3.setLayout(borderLayout44);
        pnlRoomAdvancedLayout3.add(jLabel25, BorderLayout.NORTH);
        pnlRoomAdvancedLayout3.add(txtBoundary, BorderLayout.SOUTH);
        jScrollPane9.getViewport().add(txtRoomCode, null);
        pnlRoomAdvancedLayout4.setBorder(_borderInset5);
        pnlRoomAdvancedLayout4.setLayout(borderLayout50);
        pnlRoomAdvancedLayout4.add(jScrollPane9, BorderLayout.CENTER);
        pnlRoomAdvancedLayout4.add(jLabel29, BorderLayout.NORTH);
        pnlRoomAdvancedLayout2.setBorder(_borderInset5);
        pnlRoomAdvancedLayout2.setLayout(borderLayout45);
        pnlRoomAdvancedLayout2.add(pnlRoomAdvancedLayout4, null);
        pnlRoomAdvancedLayout1.setBorder(_borderInset5);
        pnlRoomAdvancedLayout1.setLayout(verticalFlowLayout5);
        pnlRoomAdvancedLayout1.add(pnlRoomAdvancedLayout3, null);
        pnlRoomAdv.setLayout(borderLayout46);
        pnlRoomAdv.add(pnlRoomAdvancedLayout1, BorderLayout.NORTH);
        pnlRoomAdv.add(pnlRoomAdvancedLayout2, BorderLayout.CENTER);
        // EleMapper
        pnlRoomEleMapperLayout1.add(jLabel24, null);
        pnlRoomEleMapperLayout1.add(btnRoomColour, null);
        pnlRoomEleMapper.setLayout(borderLayout49);
        pnlRoomEleMapper.add(pnlRoomEleMapperLayout1, BorderLayout.WEST);

        // Add To Tabs
        tabbedPaneRoom.setBorder(_borderInset5);
        tabbedPaneRoom.add(pnlRoomBasics, "Basics");
        tabbedPaneRoom.add(pnlRoomItems, "Items");
        tabbedPaneRoom.add(pnlRoomMisc, "Miscellaneous");
        tabbedPaneRoom.add(pnlRoomSenses, "Senses");
        tabbedPaneRoom.add(pnlRoomObjects, "Loadable");
        tabbedPaneRoom.add(pnlRoomGuards, "Guards");
        tabbedPaneRoom.add(pnlRoomFunctions, "Functions");
        tabbedPaneRoom.add(pnlRoomAdv, "Advanced");
        tabbedPaneRoom.add(pnlRoomEleMapper, "EleMapper");

        // Exit tabbed pane
        pnlExitRoomNumbers.setLayout(xYLayout5);
        pnlExitRoomNumbers.add(jLabel6, new XYConstraints(5, 5, 150, -1));
        pnlExitRoomNumbers.add(jLabel7, new XYConstraints(5, 25, 150, -1));
        pnlExitRoomNumbers.add(lblExitRoom1, new XYConstraints(153, 5, 34, -1));
        pnlExitRoomNumbers.add(lblExitRoom2, new XYConstraints(153, 25, 34, -1));
        pnlExitInvisible.setLayout(xYLayout2);
        pnlExitInvisible.add(optNotInvis, new XYConstraints(5, 5, -1, -1));
        pnlExitInvisible.add(optInvis, new XYConstraints(5, 25, -1, -1));
        pnlExitInvisible.add(chkInvisibleRoom1, new XYConstraints(25, 45, -1, -1));
        pnlExitInvisible.add(chkInvisibleRoom2, new XYConstraints(25, 65, -1, -1));
        pnlExitLock.setLayout(xYLayout4);
        pnlExitLock.add(optNotLockable,   new XYConstraints(5, 5, -1, -1));
        pnlExitLock.add(optLockable, new XYConstraints(5, 25, -1, -1));
        pnlExitLock.add(jLabel5, new XYConstraints(24, 50, -1, -1));
        pnlExitLock.add(txtKeyId1, new XYConstraints(137, 50, 99, 18));
        pnlExitLock.add(chkSameKeys, new XYConstraints(24, 69, -1, 15));
        pnlExitLock.add(jLabel28, new XYConstraints(24, 85, -1, -1));
        pnlExitLock.add(txtKeyId2, new XYConstraints(137, 85, 99, 18));
        pnlExitDoor.setLayout(xYLayout3);
        pnlExitDoor.add(optNotDoor, new XYConstraints(5, 5, -1, -1));
        pnlExitDoor.add(optDoor, new XYConstraints(5, 25, -1, -1));
        pnlExitDoor.add(jLabel9, new XYConstraints(45, 45, -1, -1));
        pnlExitDoor.add(jLabel37, new XYConstraints(148, 45, -1, -1));
        pnlExitDoor.add(jLabel10, new XYConstraints(235, 45, -1, -1));
        pnlExitDoor.add(jLabel36, new XYConstraints(10, 55, -1, -1)); // name
        pnlExitDoor.add(txtDoorName1, new XYConstraints(10, 70, 140, 18));
        pnlExitDoor.add(chkSameDoorName, new XYConstraints(160, 70, -1, 15));
        pnlExitDoor.add(txtDoorName2, new XYConstraints(190, 70, 140, 18));
        pnlExitDoor.add(jLabel38, new XYConstraints(10, 87, -1, -1)); // concealed
        pnlExitDoor.add(chkConcealedRoom1, new XYConstraints(6, 102, -1, 15));
        pnlExitDoor.add(chkConcealedRoom2, new XYConstraints(186, 102, -1, 15));
        pnlExitDoor.add(jLabel39, new XYConstraints(10, 117, -1, -1)); // door desc
        pnlExitDoor.add(txtDoorDescRoom1, new XYConstraints(10, 134, 140, 18));
        pnlExitDoor.add(chkSameDoorDesc, new XYConstraints(160, 134, -1, 15));
        pnlExitDoor.add(txtDoorDescRoom2, new XYConstraints(190, 134, 140, 18));
        pnlExitDoor.add(jLabel40, new XYConstraints(10, 151, -1, -1)); // door open desc
        pnlExitDoor.add(txtDoorOpenDescRoom1, new XYConstraints(10, 166, 140, 18));
        pnlExitDoor.add(txtDoorOpenDescRoom2, new XYConstraints(190, 166, 140, 18));
        pnlExitDoor.add(jLabel41, new XYConstraints(10, 183, -1, -1)); // door closed desc
        pnlExitDoor.add(txtDoorClosedDescRoom1, new XYConstraints(10, 198, 140, 18));
        pnlExitDoor.add(txtDoorClosedDescRoom2, new XYConstraints(190, 198, 140, 18));
        pnlExitSpecificsLayout4.setLayout(borderLayout14);
        pnlExitSpecificsLayout4.add(lblExitDirection[EleUtils.translateDirectionToIndex(EleConstants.DIRECTION_EAST)], BorderLayout.EAST);
        pnlExitSpecificsLayout4.add(lblExitDirection[EleUtils.translateDirectionToIndex(EleConstants.DIRECTION_WEST)], BorderLayout.WEST);
        pnlExitSpecificsLayout4.add(pnlExitMiniMap, BorderLayout.CENTER);
        pnlExitSpecificsLayout5.setLayout(borderLayout15);
        pnlExitSpecificsLayout5.add(lblExitDirection[EleUtils.translateDirectionToIndex(EleConstants.DIRECTION_SOUTH)], BorderLayout.CENTER);
        pnlExitSpecificsLayout5.add(lblExitDirection[EleUtils.translateDirectionToIndex(EleConstants.DIRECTION_SOUTHWEST)], BorderLayout.WEST);
        pnlExitSpecificsLayout5.add(lblExitDirection[EleUtils.translateDirectionToIndex(EleConstants.DIRECTION_SOUTHEAST)], BorderLayout.EAST);
        pnlExitSpecificsLayout6.setLayout(borderLayout13);
        pnlExitSpecificsLayout6.add(lblExitDirection[EleUtils.translateDirectionToIndex(EleConstants.DIRECTION_NORTHWEST)], BorderLayout.WEST);
        pnlExitSpecificsLayout6.add(lblExitDirection[EleUtils.translateDirectionToIndex(EleConstants.DIRECTION_NORTHEAST)], BorderLayout.EAST);
        pnlExitSpecificsLayout6.add(lblExitDirection[EleUtils.translateDirectionToIndex(EleConstants.DIRECTION_NORTH)], BorderLayout.CENTER);
        pnlExitSpecificsLayout3.setLayout(borderLayout16);
        pnlExitSpecificsLayout3.add(pnlExitSpecificsLayout4, BorderLayout.CENTER);
        pnlExitSpecificsLayout3.add(pnlExitSpecificsLayout5, BorderLayout.SOUTH);
        pnlExitSpecificsLayout3.add(pnlExitSpecificsLayout6, BorderLayout.NORTH);
        pnlExitSpecificsLayout1.setBorder(_borderInset5);
        pnlExitSpecificsLayout1.setLayout(borderLayout9);
        pnlExitSpecificsLayout1.add(pnlExitSpecificsLayout3, BorderLayout.CENTER);
        pnlExitSpecificsLayout1.add(pnlExitRoomNumbers, BorderLayout.NORTH);
        tabbedPaneExitSpecifics.add(pnlExitDoor, "Door");
        tabbedPaneExitSpecifics.add(pnlExitLock, "Locking");
        tabbedPaneExitSpecifics.add(pnlExitInvisible, "Invisible");
        pnlExitSpecificsLayout2.setBorder(_borderInset5);
        pnlExitSpecificsLayout2.setLayout(borderLayout11);
        pnlExitSpecificsLayout2.add(tabbedPaneExitSpecifics, BorderLayout.CENTER);
        pnlExitSpecifics.setBorder(_borderInset5);
        pnlExitSpecifics.setLayout(borderLayout10);
        pnlExitSpecifics.add(pnlExitSpecificsLayout1, BorderLayout.WEST);
        pnlExitSpecifics.add(pnlExitSpecificsLayout2, BorderLayout.CENTER);
        // Add To Tab
        tabbedPaneExit.setBorder(_borderInset5);
        tabbedPaneExit.add(pnlExitSpecifics, "Specifics");
        // New

        // Init the variables
        initialise(null, null);
    }

    /** overridden so we can exit when window is closed. */
    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            menuFileExit.doClick();
        }
    }

    //-------------------------------------------------
    // Menu functions
    //-------------------------------------------------

    void menuFile_menuSelected(MenuEvent e) {
        updateCurrentRoom(null, false);
        updateCurrentExit(null, false);
    }

    void menuFileNew_actionPerformed(ActionEvent e) {
        initialise(new EleMap(), null);
        updateTitle();
    }

    void menuFileOpen_actionPerformed(ActionEvent e) {
        File file;
        FileChooser fileChooser = new FileChooser(FileChooser.OPEN, this, _currentDir);
        file = fileChooser.chooseFile();
        if (file != null) {
            _currentDir = file.getParentFile();
            DocumentHandler doc = new DocumentHandler(file);
            try {
                doc.readFromXML();
                initialise((EleMap) doc.getData(), file);
            } catch (Exception e1){
                JOptionPane.showMessageDialog(this,
                        "Error reading file (" + file.getAbsolutePath() +
                        "):\n" + e1,
                        "XML Read Error",
                        JOptionPane.ERROR_MESSAGE);
                e1.printStackTrace(System.err);
            }
        }
        updateTitle();
    }

    void menuFileSaveAs_actionPerformed(ActionEvent e) {
        genericSave(FileChooser.SAVE_AS);
    }

    void menuFileSave_actionPerformed(ActionEvent e) {
        genericSave(FileChooser.SAVE);
    }

    void menuFileExport_actionPerformed(ActionEvent e) {
        FileChooser fileChooser;
        File directory;

        fileChooser = new FileChooser(FileChooser.DIRECTORY, this, _currentDir);
        directory = fileChooser.chooseFile();

        if (directory != null) {
            _currentDir = directory.getParentFile();
            if (_eleMap.getPath().length() < 1) {
                menuActionSetPath.doClick();
            }

            try {
                _eleMap.export(directory, _eleMap.getPath(), null, null);
                JOptionPane.showMessageDialog(this,
                    "Export complete.",
                    "Exporting",
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (EleMapExportException e1) {
                JOptionPane.showMessageDialog(this,
                    "Error exporting to LPC:\n" + e1,
                    "Export Error",
                    JOptionPane.ERROR_MESSAGE);
                System.err.println(e1.toString());
                EleMappable ob = e1.getErrorObject();
                if (ob instanceof Room) {
                    gotoRoom((Room) ob);
                } else if (ob instanceof Exit) {
                    gotoExit((Exit) ob);
                }
            } catch (IOException e2) {
                JOptionPane.showMessageDialog(this,
                    "IO Error whilst exporting:\n" + e2,
                    "Export Error",
                    JOptionPane.ERROR_MESSAGE);
                e2.printStackTrace(System.err);
            } catch (Exception e3) {
                JOptionPane.showMessageDialog(this,
                    "Unknown Error whilst exporting:\n" + e3,
                    "Export Error",
                    JOptionPane.ERROR_MESSAGE);
                e3.printStackTrace(System.err);
            }
        }
    }

    void menuFileExit_actionPerformed(ActionEvent e) {
// todo: this is a check to see if anything has changed
//        if (_haschanged) {
//            msgbox("really want to exit?");
//        }
        System.exit(0);
    }

    void menuEditCopy_actionPerformed(ActionEvent e) {
        if (!roomHelper.hasRoom()) {
            JOptionPane.showMessageDialog(this,
                "There must be a room selected in order to do a copy.",
                "Copying",
                JOptionPane.ERROR_MESSAGE);
        } else {
            saveCurrentRoomSettings(roomHelper.getRoom());
            roomHelper.createCopyRoom(_eleMap.getRoomSize(), _eleMap.getRoomNumber());
            _eleMap.incrementRoomNumber();
        }
    }

    void menuEditPaste_actionPerformed(ActionEvent e) {
        if (!roomHelper.hasCopiedRoom()) {
            JOptionPane.showMessageDialog(this,
                "Copy a room first.",
                "Pasting",
                JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                "Click the map in the position you wish to paste the room.",
                "Pasting",
                JOptionPane.INFORMATION_MESSAGE);
            _pasting = true;
        }
    }

    void menuActionSetPath_actionPerformed(ActionEvent e) {
        String path;

        path = (String) JOptionPane.showInputDialog(this,
                            "What is the root path for this map.\n(e.g. /d/town/market)\n\n"+
                            "The path will have the additional\ndirectories (rooms/include/etc) added to it.",
                            "Enter Path",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            null,
                            _eleMap.getPath());
        if (path != null) {
            if (!path.endsWith("/")) {
                path += "/";
            }
            _eleMap.setPath(path);
        }
    }

    void menuTools_menuSelected(MenuEvent e) {
        if (!roomHelper.hasRoom()) {
            menuToolLevelExitMenu.setEnabled(false);
        } else {
            menuToolLevelExitMenu.setEnabled(true);
        }
    }

    void menuToolSelect_actionPerformed(ActionEvent e) {
        btnSelect.doClick();
    }

    void menuToolDelete_actionPerformed(ActionEvent e) {
        btnDelete.doClick();
    }

    void menuToolAddRoo_actionPerformed(ActionEvent e) {
        btnAddRoom.doClick();
    }

    void menuToolAddExit_actionPerformed(ActionEvent e) {
        btnAddExit.doClick();
    }

    void menuToolOptions_actionPerformed(ActionEvent e) {
        Options.create(this);
        pnlMap.setBackground(EleConstants.BACKGROUND_COLOUR);
        pnlExitMiniMap.setBackground(EleConstants.BACKGROUND_COLOUR);
        pnlMap.repaint();
        // todo options
    }

    void menuToolLevelExitMenu_menuSelected(MenuEvent e) {
        addLevelExitItems(menuToolLevelExitMenu);
    }

    void menuLevelAddUp_actionPerformed(ActionEvent e) {
        addVerticalExit(EleConstants.DIRECTION_UP);
    }

    void menuLevelAddDown_actionPerformed(ActionEvent e) {
        addVerticalExit(EleConstants.DIRECTION_DOWN);
    }

    void menuLevelSelectUp_actionPerformed(ActionEvent e) {
        selectVerticalExit(EleConstants.DIRECTION_UP);
    }

    void menuLevelSelectDown_actionPerformed(ActionEvent e) {
        selectVerticalExit(EleConstants.DIRECTION_DOWN);
    }

    void menuLevelDeleteUp_actionPerformed(ActionEvent e) {
        deleteVerticalExit(EleConstants.DIRECTION_UP);
    }

    void menuLevelDeleteDown_actionPerformed(ActionEvent e) {
        deleteVerticalExit(EleConstants.DIRECTION_DOWN);
    }

    void menuViewLower_actionPerformed(ActionEvent e) {
        _eleMap.setShowLower(menuViewLower.getState());
        pnlMap.repaint();
    }

    void menuViewUpper_actionPerformed(ActionEvent e) {
        _eleMap.setShowUpper(menuViewUpper.getState());
        pnlMap.repaint();
    }

    void menuViewProperties_actionPerformed(ActionEvent e) {
        if (menuViewProperties.getState()) {
            pnlContent.add(pnlPropertiesLayout, BorderLayout.SOUTH);
        } else {
            pnlContent.remove(pnlPropertiesLayout);
        }
        validate();
    }

    void menuHelpAbout_actionPerformed(ActionEvent e) {
        AboutBox dlg = new AboutBox(this, _eleImage);
        Dimension dlgSize = dlg.getPreferredSize();
        Dimension frmSize = getSize();
        Point loc = getLocation();
        dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
        dlg.setModal(true);
        dlg.setVisible(true);
    }

    void popupMenu_popupMenuCanceled(PopupMenuEvent e) {
        pnlMap.repaint();
    }

    //-------------------------------------------------
    // Button Functions
    //-------------------------------------------------

    // Levels
    void btnLevelUp_actionPerformed(ActionEvent e) {
        _eleMap.incrementLevel();
        statusBarLevel.setText(_levelText + _eleMap.getLevel());
        pnlMap.repaint();
    }

    void btnLevelDown_actionPerformed(ActionEvent e) {
        _eleMap.decrementLevel();
        statusBarLevel.setText(_levelText + _eleMap.getLevel());
        pnlMap.repaint();
    }

    // Zoom
    void btnZoomIn_actionPerformed(ActionEvent e) {
        if (_eleMap.getRoomSize() <= (EleMap.INITIAL_ROOM_SIZE * ZOOM_LEVEL)) {
            _eleMap.setRoomSize(_eleMap.getRoomSize() * 2);
            _eleMap.zoom(true);
            _size.setSize(_size.width*2, _size.height*2);
            pnlMap.setPreferredSize(_size);
            pnlMap.revalidate();
            pnlMap.repaint();
        }
    }

    void btnZoomOut_actionPerformed(ActionEvent e) {
        if ((_eleMap.getRoomSize() / 2) >= (EleMap.INITIAL_ROOM_SIZE / (ZOOM_LEVEL * 2))) {
            _eleMap.setRoomSize(_eleMap.getRoomSize() / 2);
            _eleMap.zoom(false);
            _size.setSize(_size.width/2, _size.height/2);
            pnlMap.setPreferredSize(_size);
            pnlMap.revalidate();
            pnlMap.repaint();
        }
    }

    // Item buttons
    void btnItemAdd_actionPerformed(ActionEvent e) {
        StringTokenizer st = new StringTokenizer(txtItemNames.getText(), ",");
        ArrayList<String> itemNames = new ArrayList<String>();

        while (st.hasMoreTokens()) {
            itemNames.add(st.nextToken().trim());
        }

        roomHelper.addItem(itemNames, txtItemDescription.getText().trim());

        updateItemList();
        txtItemDescription.setText("");
        txtItemNames.setText("");
    }

    void btnItemDelete_actionPerformed(ActionEvent e) {
        int index = lstItems.getSelectedIndex();

        roomHelper.removeItem(index);

        updateItemList();
        txtItemDescription.setText("");
        txtItemNames.setText("");
    }

    // Sense buttons
    void btnSenseAdd_actionPerformed(ActionEvent e) {
        addSense(findSense());
    }

    void btnSenseDelete_actionPerformed(ActionEvent e) {
        int index = lstSenses.getSelectedIndex();

        roomHelper.removeSense(index, findSense());

        updateSenseList(findSense());
        txtSenseName.setText("");
        txtSenseDescription.setText("");
    }

    // Object buttons
    void btnObjectAdd_actionPerformed(ActionEvent e) {
        roomHelper.addObject(txtObjectFileName.getText(),
                optObjectLoadPresent.isSelected(),
                optObjectLoadTrack.isSelected(),
                optObjectLoadUnique.isSelected(),
                optObjectTypeMon.isSelected(),
                txtObjectMessage.getText());

        updateObjectLists();
        txtObjectFileName.setText("");
        txtObjectMessage.setText("");
    }

    void btnObjectDelete_actionPerformed(ActionEvent e) {
        roomHelper.removeObject((String) lstObjects.getSelectedValue());

        updateObjectLists();
        txtObjectFileName.setText("");
        txtObjectMessage.setText("");
    }

    // Guard buttons
    void btnGuardAdd_actionPerformed(ActionEvent e) {
        // todo JPC
    }

    void btnGuardDelete_actionPerformed(ActionEvent e) {
        //todo JPC
    }

    // Function buttons
    void btnFunctionAdd_actionPerformed(ActionEvent e) {
        roomHelper.addFunction(txtFunctionName.getText(),
                txtFunctionReturnType.getText(),
                txtFunctionArguments.getText(),
                txtFunctionBody.getText());

        updateFunctionLists();
        txtFunctionReturnType.setText("");
        txtFunctionName.setText("");
        txtFunctionArguments.setText("");
        txtFunctionBody.setText("");
    }

    void btnFunctionDelete_actionPerformed(ActionEvent e) {
        roomHelper.removeFunction((String) lstFunctions.getSelectedValue());

        updateFunctionLists();
        txtFunctionReturnType.setText("");
        txtFunctionName.setText("");
        txtFunctionArguments.setText("");
        txtFunctionBody.setText("");
    }

    // Advanced buttons
    void btnRoomColour_actionPerformed(ActionEvent e) {
        Color c = JColorChooser.showDialog(
            this,
            "Choose a colour for this room",
            btnRoomColour.getBackground()
        );
        if (c != null) {
            btnRoomColour.setBackground(c);
            roomHelper.setColour(c);
            pnlMap.repaint();
        }
    }

    //-------------------------------------------------
    // Generic Button Functions
    //-------------------------------------------------

    void button_mouseEntered(MouseEvent e) {
        JButton button = (JButton) e.getComponent();

        if (!button.isSelected()) {
            button.setBorder(_borderButtonRaised);
        }
    }

    void button_mouseExited(MouseEvent e) {
        JButton button = (JButton) e.getComponent();

        if (!button.isSelected()) {
            button.setBorder(_borderButtonNormal);
        }
    }

    void button_mousePressed(MouseEvent e) {
        ((JButton) e.getComponent()).setBorder(_borderButtonLowered);
    }

    void button_mouseReleased(MouseEvent e) {
        JButton button = (JButton) e.getComponent();

        if (!button.isSelected()) {
            button.setBorder(_borderButtonRaised);
        }
    }

    private void button_actionPerformed(JButton button, int tool) {
        changeSelection(_currentButton);
        changeSelection(button);
        _currentButton = button;
        _currentTool = tool;
        switch (tool) {
        case TOOL_SELECT:
            statusBar.setText("Selecting");
            break;
        case TOOL_ADD_EXIT:
            statusBar.setText("Adding Exits");
            break;
        case TOOL_ADD_ROOM:
            statusBar.setText("Adding Rooms");
            break;
        case TOOL_DELETE:
            statusBar.setText("Deleting");
            break;
        default:
            statusBar.setText("Ready");
            break;
        }
    }

    private void changeSelection(JButton button) {
        if (button != null) {
            if (button.isSelected()) {
                button.setSelected(false);
                button.setBorder(_borderButtonNormal);
            } else {
                button.setSelected(true);
                button.setBorder(_borderButtonLowered);
            }
        }
    }

    //-------------------------------------------------
    // Map Functions
    //-------------------------------------------------

    void map_mousePressed(MouseEvent e) {
        Point p = new Point(e.getX(), e.getY());

        switch(_currentTool) {
        case TOOL_ADD_EXIT:
            updateCurrentExit(p, false);
            updateCurrentRoom(p, true);

            if (roomHelper.hasRoom()) {
                _exitStartPoint = p;
                _makingExit = true;
            }
            break;
        case TOOL_SELECT:
            updateCurrentExit(p, false);
            updateCurrentRoom(p, true);

            if (roomHelper.hasRoom()) {
                _movingStuff = true;
                _stuffToMove = Room.getAllLinked(new EleMappableCollection<EleMappable>(EleConstants.XML_BOTH), roomHelper.getRoom());
            }
            break;
        }
    }

    void map_mouseDragged(MouseEvent e) {
        Graphics2D g;
        float[] dash1 = {4.0f};
        BasicStroke bs;
        int x;
        int y;

        if (_makingExit || _movingStuff) {
            bs = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);
            g = (Graphics2D) pnlMap.getGraphics();
            pnlMap.paint(g);
            g.setStroke(bs);
            g.setColor(EleConstants.SELECT_COLOUR);

            if (_makingExit) {
                g.drawLine(_exitStartPoint.x, _exitStartPoint.y, e.getX(), e.getY());
            } else if (_movingStuff) {
                x = e.getX() - (_eleMap.getRoomSize() / 2) - (int) roomHelper.getRoom().getX();
                y = e.getY() - (_eleMap.getRoomSize() / 2) - (int) roomHelper.getRoom().getY();

                _stuffToMove.paintOutline(g, _eleMap.getLevel(), x, y);
            }
        }
    }

    void map_mouseReleased(MouseEvent e) {
        Point p = new Point(e.getX(), e.getY());
        Point p2;
        EleMappable tmp;
        boolean move = true;
        Room r;
        int direction;

        // Make sure the cursor is still in the bounds of the map.
        if (pnlMap.contains(p)) {
            if (e.isPopupTrigger()) {
                if (!_pasting) {
                    updateCurrentExit(p, false);
                    updateCurrentRoom(p, true);
                    if (roomHelper.hasRoom()) {
                        _makingExit = false;
                        _movingStuff = false;
                        addLevelExitItems(popupMenu);
                        popupMenu.show(pnlMap, e.getX(), e.getY());
                    }
                }
            } else {
                if (_makingExit) {
                    _makingExit = false;
                    Room roomExitLinkedFrom = roomHelper.getRoom();
                    updateCurrentRoom(p, true);
                    Room roomExitLinkedTo = roomHelper.getRoom();

                    if (roomExitLinkedTo != null) {
                        direction = roomExitLinkedFrom.adjacentRoom(roomExitLinkedTo);
                        if ((direction != 0) && (!roomExitLinkedFrom.containsExit(direction))) {
                            clearRoomSettings();
                            roomHelper.deSelect(_mapGraphics, _eleMap.getLevel(), _eleMap.getShowUpper(), _eleMap.getShowLower());
                            exitHelper.setExit(new Exit(roomExitLinkedFrom, roomExitLinkedTo, _eleMap.getExitNumber(), _eleMap.getLevel(), _mapGraphics));
                            _eleMap.getExits().add(exitHelper.getExit());
                            _eleMap.incrementExitNumber();
                            updateExitSettings(exitHelper.getExit());
                        }
                    }

                    pnlMap.repaint();
                } else if (_movingStuff) {
                    _movingStuff = false;
                    p = Room.alterForRoom(p, _eleMap.getRoomSize());

                    // Check if we are overlapping any rooms.
                    for (Object thingToMove : _stuffToMove) {
                        tmp = (EleMappable) thingToMove;
                        if (tmp instanceof Room) {
                            p2 = ((Room) tmp).getLocation();
                            p2.translate((int) (p.getX() - roomHelper.getRoom().getX()),
                                    (int) (p.getY() - roomHelper.getRoom().getY()));
                            r = (Room) _eleMap.getRooms().exists(p2, _eleMap.getLevel());
                            if (r != null && !_stuffToMove.contains(r)) {
                                move = false;
                            }
                        }
                    }

                    // If everything is ok, then do the move.
                    if (move) {
                        _stuffToMove.shift((int) (p.getY() - roomHelper.getRoom().getY()),
                                            (int) (p.getX() - roomHelper.getRoom().getX()));
                    }

                    // ...and repaint.
                    pnlMap.repaint();
                }
            }
        }

        _exitStartPoint = null;
    }

    void map_mouseClicked(MouseEvent e) {
        Point p = new Point(e.getX(), e.getY());
        int retValue;
        EleMappableCollection<EleMappable> tmp;

        if (_pasting) {
            updateCurrentRoom(p, true);
            updateCurrentExit(p, false);
            if (roomHelper.hasRoom()) {
                retValue = JOptionPane.showConfirmDialog(this,
                               "There is already a room there. Click ok to update that room with the details from the copied room.",
                               "Warning",
                               JOptionPane.OK_CANCEL_OPTION);
                if (retValue == JOptionPane.OK_OPTION) {
                    roomHelper.updateCurrentRoomWithCopiedRoom();
                    updateRoomSettings(roomHelper.getRoom());
                }
            } else {
                roomHelper.makeCopiedRoomTheCurrentRoom();
                Room room = roomHelper.getRoom();
                p = Room.alterForRoom(p, _eleMap.getRoomSize());
                room.setLevel(_eleMap.getLevel());
                room.shift((int) (p.getY() - room.getY()),
                                   (int) (p.getX() - room.getX()));
                _eleMap.getRooms().add(room);
                room.select(_mapGraphics);
                updateRoomSettings(room);
            }
        } else {
            switch(_currentTool) {
                case TOOL_SELECT:
                    updateCurrentRoom(p, true);
                    updateCurrentExit(p, !roomHelper.hasRoom());
                    break;
                case TOOL_DELETE:
                    updateCurrentRoom(p, true);
                    updateCurrentExit(p, !roomHelper.hasRoom());
                    if (roomHelper.hasRoom()) {
                        Room room = roomHelper.getRoom();
                        if (room.getNumberOfExits() == 0) {
                            clearRoomSettings();
                            _eleMap.getRooms().remove(room);
                            roomHelper.clearRoom();
                        } else {
                            retValue = JOptionPane.showConfirmDialog(this,
                                           "This room has exits. Deleting this room will delete everything linked to it.",
                                           "Warning",
                                           JOptionPane.OK_CANCEL_OPTION);
                            if (retValue == JOptionPane.OK_OPTION) {
                                clearRoomSettings();
                                tmp = Room.getAllLinked(new EleMappableCollection<EleMappable>(EleConstants.XML_BOTH), room);
                                _eleMap.getRooms().removeAll(tmp);
                                _eleMap.getExits().removeAll(tmp);
                                roomHelper.clearRoom();
                            }
                        }
                    } else if (exitHelper.hasExit()) {
                        clearExitSettings();
                        _eleMap.getExits().remove(exitHelper.getExit());
                        exitHelper.clearExit();
                    }
                    button_actionPerformed(btnSelect, TOOL_SELECT);
                    break;
                case TOOL_ADD_ROOM:
                    p = Room.alterForRoom(p, _eleMap.getRoomSize());
                    updateCurrentExit(p, false);
                    updateCurrentRoom(p, true);
                    if (!roomHelper.hasRoom()) {
                        roomHelper.setRoom(new Room(p, _eleMap.getRoomSize(), _eleMap.getRoomNumber(), _eleMap.getLevel(), _mapGraphics));
                        _eleMap.getRooms().add(roomHelper.getRoom());
                        _eleMap.incrementRoomNumber();
                        updateRoomSettings(roomHelper.getRoom());
                    }
                    break;
            }
        }
        _pasting = false;
        pnlMap.repaint();
    }

    //-------------------------------------------------
    // Option/Checkbox functions
    //-------------------------------------------------

    void optNotInvis_actionPerformed(ActionEvent e) {
        chkInvisibleRoom1.setSelected(true);
        chkInvisibleRoom2.setSelected(true);
        chkInvisibleRoom1.setEnabled(false);
        chkInvisibleRoom2.setEnabled(false);
    }

    void optInvis_actionPerformed(ActionEvent e) {
        chkInvisibleRoom1.setEnabled(true);
        chkInvisibleRoom2.setEnabled(true);
    }

    void chkInvisibleRoom1_actionPerformed(ActionEvent e) {
        chkExitAttribute_actionPerformed(chkInvisibleRoom1, chkInvisibleRoom2, optNotInvis);
    }

    void chkInvisibleRoom2_actionPerformed(ActionEvent e) {
        chkExitAttribute_actionPerformed(chkInvisibleRoom1, chkInvisibleRoom2, optNotInvis);
    }

    void optNotDoor_actionPerformed(ActionEvent e) {
        optNotLockable.doClick();
        txtDoorName1.setText("");
        txtDoorName1.setBackground(EleConstants.DISABLED_COLOUR);
        txtDoorName1.setEnabled(false);
        chkSameDoorName.setSelected(true);
        chkSameDoorName.setEnabled(false);
        txtDoorName2.setText("");
        txtDoorName2.setBackground(EleConstants.DISABLED_COLOUR);
        txtDoorName2.setEnabled(false);
        chkConcealedRoom1.setSelected(false);
        chkConcealedRoom1.setEnabled(false);
        chkConcealedRoom2.setSelected(false);
        chkConcealedRoom2.setEnabled(false);
        txtDoorDescRoom1.setText("");
        txtDoorDescRoom1.setBackground(EleConstants.DISABLED_COLOUR);
        txtDoorDescRoom1.setEnabled(false);
        chkSameDoorDesc.setSelected(true);
        chkSameDoorDesc.setEnabled(false);
        txtDoorDescRoom2.setText("");
        txtDoorDescRoom2.setBackground(EleConstants.DISABLED_COLOUR);
        txtDoorDescRoom2.setEnabled(false);
        txtDoorOpenDescRoom1.setText("");
        txtDoorOpenDescRoom1.setBackground(EleConstants.DISABLED_COLOUR);
        txtDoorOpenDescRoom1.setEnabled(false);
        txtDoorOpenDescRoom2.setText("");
        txtDoorOpenDescRoom2.setBackground(EleConstants.DISABLED_COLOUR);
        txtDoorOpenDescRoom2.setEnabled(false);
        txtDoorClosedDescRoom1.setText("");
        txtDoorClosedDescRoom1.setBackground(EleConstants.DISABLED_COLOUR);
        txtDoorClosedDescRoom1.setEnabled(false);
        txtDoorClosedDescRoom2.setText("");
        txtDoorClosedDescRoom2.setBackground(EleConstants.DISABLED_COLOUR);
        txtDoorClosedDescRoom2.setEnabled(false);
        optLockable.setEnabled(false);
        optNotLockable.setEnabled(false);
    }

    void optDoor_actionPerformed(ActionEvent e) {
        chkSameDoorName.setEnabled(true);
        txtDoorName1.setEnabled(true);
        txtDoorName1.setBackground(EleConstants.ENABLED_COLOUR);
        chkConcealedRoom1.setEnabled(true);
        chkConcealedRoom2.setEnabled(true);
        chkSameDoorDesc.setEnabled(true);
        txtDoorDescRoom1.setEnabled(true);
        txtDoorDescRoom1.setBackground(EleConstants.ENABLED_COLOUR);
        txtDoorOpenDescRoom1.setEnabled(true);
        txtDoorOpenDescRoom1.setBackground(EleConstants.ENABLED_COLOUR);
        txtDoorClosedDescRoom1.setEnabled(true);
        txtDoorClosedDescRoom1.setBackground(EleConstants.ENABLED_COLOUR);
        optLockable.setEnabled(true);
        optNotLockable.setEnabled(true);
    }

    void optNotLockable_actionPerformed(ActionEvent e) {
        txtKeyId1.setText("");
        txtKeyId1.setBackground(EleConstants.DISABLED_COLOUR);
        txtKeyId1.setEnabled(false);
        txtKeyId2.setText("");
        txtKeyId2.setBackground(EleConstants.DISABLED_COLOUR);
        txtKeyId2.setEnabled(false);
        chkSameKeys.setSelected(true);
        chkSameKeys.setEnabled(false);
    }

    void optLockable_actionPerformed(ActionEvent e) {
        txtKeyId1.setEnabled(true);
        txtKeyId1.setBackground(EleConstants.ENABLED_COLOUR);
        chkSameKeys.setEnabled(true);
    }

    void chkSameDoorName_actionPerformed(ActionEvent e) {
        if (chkSameDoorName.isSelected()) {
            txtDoorName2.setEnabled(false);
            txtDoorName2.setBackground(EleConstants.DISABLED_COLOUR);
            txtDoorName2.setText("");
        } else {
            txtDoorName2.setEnabled(true);
            txtDoorName2.setBackground(EleConstants.ENABLED_COLOUR);
        }
    }

    void chkSameDoorDesc_actionPerformed(ActionEvent e) {
        if (chkSameDoorDesc.isSelected()) {
            txtDoorDescRoom2.setEnabled(false);
            txtDoorDescRoom2.setBackground(EleConstants.DISABLED_COLOUR);
            txtDoorDescRoom2.setText("");
            txtDoorOpenDescRoom2.setEnabled(false);
            txtDoorOpenDescRoom2.setBackground(EleConstants.DISABLED_COLOUR);
            txtDoorOpenDescRoom2.setText("");
            txtDoorClosedDescRoom2.setEnabled(false);
            txtDoorClosedDescRoom2.setBackground(EleConstants.DISABLED_COLOUR);
            txtDoorClosedDescRoom2.setText("");
        } else {
            txtDoorDescRoom2.setEnabled(true);
            txtDoorDescRoom2.setBackground(EleConstants.ENABLED_COLOUR);
            txtDoorOpenDescRoom2.setEnabled(true);
            txtDoorOpenDescRoom2.setBackground(EleConstants.ENABLED_COLOUR);
            txtDoorClosedDescRoom2.setEnabled(true);
            txtDoorClosedDescRoom2.setBackground(EleConstants.ENABLED_COLOUR);
        }
    }

    void chkSameKeys_actionPerformed(ActionEvent e) {
        if (chkSameKeys.isSelected()) {
            txtKeyId2.setEnabled(false);
            txtKeyId2.setBackground(EleConstants.DISABLED_COLOUR);
            txtKeyId2.setText("");
        } else {
            txtKeyId2.setEnabled(true);
            txtKeyId2.setBackground(EleConstants.ENABLED_COLOUR);
        }
    }

    /** Used by all Exit checkboxes, because they all perform the same function. */
    void chkExitAttribute_actionPerformed(JCheckBox room1, JCheckBox room2, JRadioButton notOption) {
        if (!room1.isSelected() && !room2.isSelected()) {
            notOption.doClick();
        }
    }

    void optIndoors_actionPerformed(ActionEvent e) {
        lstTerrains.setSelectedIndices(new int[] {-1});
        lstTerrains.setBackground(EleConstants.DISABLED_COLOUR);
        lstTerrains.setEnabled(false);
    }

    void optOutdoors_actionPerformed(ActionEvent e) {
        lstTerrains.setBackground(EleConstants.ENABLED_COLOUR);
        lstTerrains.setEnabled(true);
        lstTerrains.setSelectedIndex(0);
    }

    void optSmells_itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.DESELECTED) {
            saveSenses(roomHelper.getRoom().getSmells());
        } else if (e.getStateChange() == ItemEvent.SELECTED) {
            if (_senseSelected != optSmells) {
                loadSenses(roomHelper.getRoom().getSmells());
                _senseSelected = optSmells;
            }
        }
    }

    void optSounds_itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.DESELECTED) {
            saveSenses(roomHelper.getRoom().getSounds());
        } else if (e.getStateChange() == ItemEvent.SELECTED) {
            if (_senseSelected != optSounds) {
                loadSenses(roomHelper.getRoom().getSounds());
                _senseSelected = optSounds;
            }
        }
    }

    //-------------------------------------------------
    // Other Controls functions
    //-------------------------------------------------

    void lstItems_valueChanged(ListSelectionEvent e) {
        int index = lstItems.getSelectedIndex();
        if (index >= 0) {
            if (roomHelper.hasRoom()) {
                txtItemDescription.setText(roomHelper.getRoom().getItem(index).getDescription());
                txtItemNames.setText(roomHelper.getRoom().getItem(index).getNames());
            }
        }
    }

    void lstSenses_valueChanged(ListSelectionEvent e) {
        EleHashtable senses;
        int index = lstSenses.getSelectedIndex();
        if (index >= 0) {
            senses = findSense();
            if (senses != null) {
                txtSenseName.setText(senses.getKey(index));
                txtSenseDescription.setText(senses.getValue(index));
            }
        }
    }

    void lstObjects_valueChanged(ListSelectionEvent e) {
        LoadedObject loadedObject = (LoadedObject) lstObjects.getSelectedValue();
        int tmp;

        if (loadedObject != null) {
            txtObjectFileName.setText(loadedObject.getFileName());
            txtObjectMessage.setText(loadedObject.getMessage());

            tmp = loadedObject.getObjectType();
            if (tmp == LoadedObject.OBJECT_TYPE_MON) {
                optObjectTypeMon.setSelected(true);
            } else {
                optObjectTypeObj.setSelected(true);
            }

            tmp = loadedObject.getLoadType();
            if (tmp == LoadedObject.LOAD_TYPE_PRESENT) {
                optObjectLoadPresent.setSelected(true);
            } else if (tmp == LoadedObject.LOAD_TYPE_TRACK) {
                optObjectLoadTrack.setSelected(true);
            } else {
                optObjectLoadUnique.setSelected(true);
            }
        }
    }

    void lstFunctions_valueChanged(ListSelectionEvent e) {
        Function function = (Function) lstFunctions.getSelectedValue();

        if (function != null) {
            txtFunctionReturnType.setText(function.getReturnType());
            txtFunctionName.setText(function.getName());
            txtFunctionArguments.setText(function.getArguments());
            txtFunctionBody.setText(function.getBody());
        }
    }

    //-------------------------------------------------
    // Private functions
    //-------------------------------------------------

    private void updateTitle() {
//        this.setTitle("Ele Mapper (V"+EleConstants.VERSION+")"+(_currentFile == null?"":" - "+_currentFile.getAbsolutePath())); //todo Comment out for designer
    }

    private void menuSetup(AbstractButton menuItem, String laf) {
        menuItem.addActionListener(new ChangeLookAndFeelAction(this, laf));
        menuItem.setEnabled(isAvailableLookAndFeel(laf));
        buttonGrouplafMenu.add(menuItem);
        if (laf.equals(UIManager.getSystemLookAndFeelClassName())) menuItem.setSelected(true);
    }

    /**
     * A utility function that layers on top of the LookAndFeel's
     * isSupportedLookAndFeel() method. Returns true if the LookAndFeel
     * is supported. Returns false if the LookAndFeel is not supported
     * and/or if there is any kind of error checking if the LookAndFeel
     * is supported.
     *
     * The L&F menu will use this method to detemine whether the various
     * L&F options should be active or inactive.
     *
     */
    private boolean isAvailableLookAndFeel(String laf) {
        try {
            Class lnfClass = Class.forName(laf);
            LookAndFeel newLAF = (LookAndFeel) lnfClass.newInstance();
            return newLAF.isSupportedLookAndFeel();
        } catch(Exception e) { // If ANYTHING weird happens, return false
            return false;
        }
    }

    /**
     * Stores the current L&F, and calls updateLookAndFeel, below
     */
    public void setLookAndFeel(String laf) {
        if (!_currentLookAndFeel.equals(laf)) {
            _currentLookAndFeel = laf;
            updateLookAndFeel();
        }
    }

    /**
     * Sets the current L&F
     */
    public void updateLookAndFeel() {
        try {
            UIManager.setLookAndFeel(_currentLookAndFeel);
            SwingUtilities.updateComponentTreeUI(this);
            SwingUtilities.updateComponentTreeUI(tabbedPaneExit);
            SwingUtilities.updateComponentTreeUI(tabbedPaneRoom);
        } catch (Exception ex) {
            System.out.println("Failed loading L&F: " + _currentLookAndFeel);
            System.out.println(ex);
        }
    }

    private void initialise(EleMap newMap, File currentFile) {
        int x;

        _currentLookAndFeel = UIManager.getSystemLookAndFeelClassName();
        button_actionPerformed(btnSelect, TOOL_SELECT);
        _currentFile = currentFile;
        _stuffToMove = null;
        roomHelper = new RoomHelper();
        exitHelper = new ExitHelper();
        _exitStartPoint = null;
        _makingExit = false;
        _movingStuff = false;
        if (newMap == null) {
            _eleMap = new EleMap();
        } else {
            _eleMap = newMap;
        }
        statusBarLevel.setText(_levelText + _eleMap.getLevel());
        lstTerrains.setListData(Room.TERRAINS);

        x = _eleMap.getRoomSize() * NUMBER_OF_ROOMS_ON_MAP;
        _size.setSize(x, x);
        pnlMap.setPreferredSize(_size);
        pnlMap.repaint();
    }

    private void paintMiniExitMap(Graphics miniMap) {
        if (exitHelper.hasExit()) {
            Exit currentExit = exitHelper.getExit();
            int x = pnlExitMiniMap.getInsets().left;
            int y = pnlExitMiniMap.getInsets().top;
            int height = pnlExitMiniMap.getHeight();
            int width = pnlExitMiniMap.getWidth();
            Point p1 = EleUtils.getPointForDirection(currentExit.getDirection(currentExit.getRoom1()), x, y, height, width);
            Point p2 = EleUtils.getPointForDirection(currentExit.getDirection(currentExit.getRoom2()), x, y, height, width);
            miniMap.setColor(EleConstants.EXIT_COLOUR);
            miniMap.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
    }

    private void addLevelExitItems(JComponent menu) {
        if (roomHelper.hasRoom()) {
            menu.removeAll();

            if (!roomHelper.getRoom().containsExit(EleConstants.DIRECTION_UP)) {
                menu.add(menuLevelAddUp);
            } else {
                menu.add(menuLevelSelectUp);
                menu.add(menuLevelDeleteUp);
            }

            if (!roomHelper.getRoom().containsExit(EleConstants.DIRECTION_DOWN)) {
                menu.add(menuLevelAddDown);
            } else {
                menu.add(menuLevelSelectDown);
                menu.add(menuLevelDeleteDown);
            }
        }
    }

    private void genericSave(int option) {
        FileChooser fileChooser;
        DocumentHandler doc;

        if (option == FileChooser.SAVE_AS || _currentFile == null) {
            fileChooser = new FileChooser(option, this, _currentDir);
            _currentFile = fileChooser.chooseFile();
        }

        if (_currentFile != null) {
            _currentDir = _currentFile.getParentFile();
            doc = new DocumentHandler(_currentFile);
            doc.setData(_eleMap);
            try {
                doc.writeToXML();
            } catch (Exception e){
                JOptionPane.showMessageDialog(
                    this,
                    "Error (" + e + ") writing file (" +
                    _currentFile.getAbsolutePath() + "): " +
                    e.getMessage(), // todo JPC e.getStackTrace()[0],
                    "XML Write Error",
                    JOptionPane.ERROR_MESSAGE);
                e.printStackTrace(System.err);
                _currentFile = null;
            }
            updateTitle();
        }
    }

    private void updateCurrentRoom(Point p, boolean shouldFind) {
        Room newRoom = null;

        if (shouldFind) {
            newRoom = (Room) _eleMap.getRooms().exists(p, _eleMap.getLevel());
        }

        if (roomHelper.hasRoom()) {
            Room currentRoom = roomHelper.getRoom();
            if ((newRoom != null) && (currentRoom.equals(newRoom))) {
                return;
            }
            saveCurrentRoomSettings(currentRoom);
            roomHelper.deSelect(_mapGraphics, _eleMap.getLevel(), _eleMap.getShowUpper(), _eleMap.getShowLower());
        }

        selectRoom(newRoom);
    }

    private void selectRoom(Room r) {
        roomHelper.setRoom(r);
        if (roomHelper.hasRoom()) { // if the room was not null
            r.select(_mapGraphics);
            updateRoomSettings(r);
        }
    }

    private void gotoRoom(Room r) {
        if (r != null) {
            selectRoom(r);
            _eleMap.setLevel(roomHelper.getRoom().getLevel());
            statusBarLevel.setText(_levelText + _eleMap.getLevel());
            pnlMap.repaint();
        }
    }

    private void saveCurrentRoomSettings(Room room) {
        int[] tmp;

        if (room != null) {
            room.setCodeDescription(txtRoomCodeDesc.getText().trim());
            room.setShortDescription(txtRoomShortDesc.getText().trim());
            room.setLongDescription(txtRoomLongDesc.getText().trim());
            room.setRoomName(txtRoomName.getText().trim());
            room.setLight(sldrLightLevel.getValue());
            saveSenses(findSense(room));
            btnItemAdd.doClick();
            btnObjectAdd.doClick();
            btnFunctionAdd.doClick();
            room.setBoundary(txtBoundary.getText().trim());
            room.setExtraCode(txtRoomCode.getText().trim());

            room.resetTerrains();
            tmp = lstTerrains.getSelectedIndices();
            for (int i = 0; i < tmp.length && i < Room.MAX_TERRAINS; i++) {
                room.setTerrain(i, tmp[i]);
            }
            clearRoomSettings();
        }
    }

    private void clearRoomSettings() {
        pnlProperties.remove(tabbedPaneRoom);
        if (menuViewProperties.isSelected()) {
            pnlProperties.paint(pnlProperties.getGraphics());
        }
        _senseSelected = null;
    }

    private void updateRoomSettings(Room room) {
        if (room != null) {
            lblRoomNumber.setText("" + room.getRoomNumber());
            txtRoomCodeDesc.setText(room.getCodeDescription());
            txtRoomShortDesc.setText(room.getShortDescription());
            txtRoomLongDesc.setText(room.getLongDescription());
            txtRoomName.setText(room.getRoomName());
            updateItemList();
            sldrLightLevel.setValue(room.getLight());
            if (room.isIndoors()) {
                optIndoors.doClick();
            } else {
                optOutdoors.doClick();
            }
            txtBoundary.setText(room.getBoundary());
            txtRoomCode.setText(room.getExtraCode());
            lstTerrains.setSelectedIndices(room.getTerrains());
            loadSenses(findSense(room));
            optObjectTypeMon.setSelected(true);
            optObjectLoadTrack.setSelected(true);
            updateObjectLists();
            updateFunctionLists();
            optGuardCheckDefault.setSelected(true);
            optGuardMessageDefault.setSelected(true);
            optGuardWizardDefault.setSelected(true);
            updateDirectionList();
            btnRoomColour.setBackground(room.getColour());

            if (pnlProperties.getComponentCount() == 0) {
                tabbedPaneRoom.setSize(pnlProperties.getSize());
                if (_currentTool != TOOL_SELECT) {
                    tabbedPaneRoom.setSelectedIndex(DEFAULT_ROOM_PANE);
                }
                pnlProperties.add(tabbedPaneRoom, BorderLayout.CENTER);
            }

            if (menuViewProperties.isSelected()) {
                pnlProperties.paint(pnlProperties.getGraphics());
            }
        }
    }

    private void updateCurrentExit(Point p, boolean shouldFind) {
        Exit e = null;

        if (shouldFind) {
            e = (Exit) _eleMap.getExits().exists(p, _eleMap.getLevel());
        }

        if (exitHelper.hasExit()) {
            Exit currentExit = exitHelper.getExit();
            if ((e != null) && (currentExit.equals(e))) {
                return;
            }
            saveCurrentExitSettings(currentExit);
            exitHelper.deSelect(_mapGraphics, _eleMap.getLevel(), _eleMap.getShowUpper(), _eleMap.getShowLower());
        }

        selectExit(e);
    }

    private void selectExit(Exit exit) {
        exitHelper.setExit(exit);
        if (exitHelper.hasExit()) { // if the exit was not null
            exit.select(_mapGraphics);
            updateExitSettings(exit);
        }
    }

    private void gotoExit(Exit e) {
        if (e != null) {
            selectExit(e);
            _eleMap.setLevel(exitHelper.getExit().getRoom1().getLevel());
            statusBarLevel.setText(_levelText + _eleMap.getLevel());
            pnlMap.repaint();
        }
    }

    private void saveCurrentExitSettings(Exit exit) {
        if (optNotInvis.isSelected()) {
            exit.setInvisible(EleConstants.EXIT_OPTION_NO);
        } else {
            if (chkInvisibleRoom1.isSelected()) {
                if (chkInvisibleRoom2.isSelected()) {
                    exit.setInvisible(EleConstants.EXIT_OPTION_YES);
                } else {
                    exit.setInvisible(EleConstants.EXIT_OPTION_ROOM1);
                }
            } else {
                exit.setInvisible(EleConstants.EXIT_OPTION_ROOM2);
            }
        }

        if (optNotDoor.isSelected()) {
            exit.setDoor(EleConstants.EXIT_OPTION_NO);
        } else {
            exit.setDoor(EleConstants.EXIT_OPTION_YES);
            exit.setDoorName1(txtDoorName1.getText().toLowerCase());
            if (chkSameDoorName.isSelected()) {
                exit.setDoorName2(txtDoorName1.getText().toLowerCase());
            } else {
                exit.setDoorName2(txtDoorName2.getText().toLowerCase());
            }

            exit.setDoorDescription1(txtDoorDescRoom1.getText().toLowerCase());
            exit.setDoorOpenDescription1(txtDoorOpenDescRoom1.getText().toLowerCase());
            exit.setDoorClosedDescription1(txtDoorClosedDescRoom1.getText().toLowerCase());
            if (chkSameDoorDesc.isSelected()) {
                exit.setDoorDescription2(txtDoorDescRoom1.getText().toLowerCase());
                exit.setDoorOpenDescription2(txtDoorOpenDescRoom1.getText().toLowerCase());
                exit.setDoorClosedDescription2(txtDoorClosedDescRoom1.getText().toLowerCase());
            } else {
                exit.setDoorDescription2(txtDoorDescRoom2.getText().toLowerCase());
                exit.setDoorOpenDescription2(txtDoorOpenDescRoom2.getText().toLowerCase());
                exit.setDoorClosedDescription2(txtDoorClosedDescRoom2.getText().toLowerCase());
            }

            if (chkConcealedRoom1.isSelected()) {
                if (chkConcealedRoom2.isSelected()) {
                    exit.setConcealed(EleConstants.EXIT_OPTION_YES);
                } else {
                    exit.setConcealed(EleConstants.EXIT_OPTION_ROOM1);
                }
            } else {
                if (chkConcealedRoom2.isSelected()) {
                    exit.setConcealed(EleConstants.EXIT_OPTION_ROOM2);
                }
            }

            if (optNotLockable.isSelected()) {
                exit.setLockable(false);
            } else {
                exit.setLockable(true);
                exit.setKeyFromRoom1(txtKeyId1.getText());
                if (chkSameKeys.isSelected()) {
                    exit.setKeyFromRoom2(txtKeyId1.getText());
                } else {
                    exit.setKeyFromRoom2(txtKeyId2.getText());
                }
            }
        }

        clearExitSettings();
    }

    private void clearExitSettings() {
        pnlProperties.remove(tabbedPaneExit);
        if (menuViewProperties.isSelected()) {
            pnlProperties.paint(pnlProperties.getGraphics());
        }

        optNotInvis.doClick();
        optNotDoor.doClick();

        for (int i = 0; i < 10; i++) {
            lblExitDirection[i].setText(EleConstants.DIRECTION_ABBREV[i]);
        }
    }

    private void updateExitSettings(Exit exit) {
        boolean door = true;
        Room room1 = exit.getRoom1();
        Room room2 = exit.getRoom2();
        String room1Number = String.valueOf(room1.getRoomNumber());
        String room2Number = String.valueOf(room2.getRoomNumber());
        long exitLevel = exit.getExitLevel();

//        lblExitNumber.setText(String.valueOf(_currentExit.getExitNumber()));
        lblExitRoom1.setText(room1Number);
        lblExitRoom2.setText(room2Number);

        if (exit.isVerticalExit()) {
            for (int i = 0; i < 10; i++) {
                lblExitDirection[i].setText("");
            }
            lblExitDirection[EleUtils.translateDirectionToIndex(EleConstants.DIRECTION_NORTH)].setText((exitLevel==room1.getLevel()?room1Number:room2Number) + " (up)");
            lblExitDirection[EleUtils.translateDirectionToIndex(EleConstants.DIRECTION_SOUTH)].setText((exitLevel!=room1.getLevel()?room1Number:room2Number) + " (down)");
        } else {
            lblExitDirection[EleUtils.translateDirectionToIndex(EleUtils.getOppositeDirection(exit.getDirection(room1)))].setText(room1Number);
            lblExitDirection[EleUtils.translateDirectionToIndex(EleUtils.getOppositeDirection(exit.getDirection(room2)))].setText(room2Number);
        }

        switch (exit.getInvisible()) {
            case EleConstants.EXIT_OPTION_NO:
                optNotInvis.doClick();
                break;
            case EleConstants.EXIT_OPTION_YES:
                optInvis.doClick();
                break;
            case EleConstants.EXIT_OPTION_ROOM1:
                optInvis.doClick();
                chkInvisibleRoom2.doClick();
                break;
            case EleConstants.EXIT_OPTION_ROOM2:
                optInvis.doClick();
                chkInvisibleRoom1.doClick();
                break;
        }

        switch (exit.getDoor()) {
            case EleConstants.EXIT_OPTION_NO:
                door = false;
                optNotLockable.doClick();
                optNotDoor.doClick();
                break;
            case EleConstants.EXIT_OPTION_YES:
                optDoor.doClick();
                break;
        }

        if (door) {
            txtDoorName1.setText(exit.getDoorName1());
            if (!exit.isDoorSame()) {
                chkSameDoorName.doClick();
                txtDoorName2.setText(exit.getDoorName2());
            }

            txtDoorDescRoom1.setText(exit.getDoorDescription1());
            txtDoorOpenDescRoom1.setText(exit.getDoorOpenDescription1());
            txtDoorClosedDescRoom1.setText(exit.getDoorClosedDescription1());
            if (!exit.isDoorDescriptionSame()) {
                chkSameDoorDesc.doClick();
                txtDoorDescRoom2.setText(exit.getDoorDescription2());
                txtDoorOpenDescRoom2.setText(exit.getDoorOpenDescription2());
                txtDoorClosedDescRoom2.setText(exit.getDoorClosedDescription2());
            }

            switch (exit.getConcealed()) {
                case EleConstants.EXIT_OPTION_YES:
                    chkConcealedRoom1.doClick();
                    chkConcealedRoom2.doClick();
                    break;
                case EleConstants.EXIT_OPTION_ROOM1:
                    chkConcealedRoom1.doClick();
                    break;
                case EleConstants.EXIT_OPTION_ROOM2:
                    chkConcealedRoom2.doClick();
                    break;
            }

            if (exit.isLockable()) {
                optLockable.doClick();
                txtKeyId1.setText(exit.getKeyFromRoom1());
                if (!exit.isKeySame()) {
                    chkSameKeys.doClick();
                    txtKeyId2.setText(exit.getKeyFromRoom2());
                }
            } else {
                optNotLockable.doClick();
            }
        }

        if (pnlProperties.getComponentCount() == 0) {
            tabbedPaneExit.setSize(pnlProperties.getSize());
            tabbedPaneExit.setSelectedIndex(DEFAULT_EXIT_PANE);
            pnlProperties.add(tabbedPaneExit, BorderLayout.CENTER);
        }

        if (menuViewProperties.isSelected()) {
            pnlProperties.paint(pnlProperties.getGraphics());
        }
    }

    private void addVerticalExit(int direction) {
        Room r;
        long level;
        boolean up = (direction == EleConstants.DIRECTION_UP);

        // Change tool to selecting.
        button_actionPerformed(btnSelect, TOOL_SELECT);
        if (roomHelper.hasRoom()) {
            Room currentRoom = roomHelper.getRoom();
            level = currentRoom.getLevel();
            r = (Room) _eleMap.getRooms().exists(currentRoom.getLocation(),
                                                  (up?level+1:level-1));
            if (r == null) {
                JOptionPane.showMessageDialog(this,
                        "There is no room directly "+
                        (up?"above":"below")+
                        " the currently selected one, therefore you cannot add "+
                        (up?"an up":"a down")+" exit.",
                        "Exit Warning",
                        JOptionPane.WARNING_MESSAGE);
            } else if (!currentRoom.containsExit(direction)) {
                saveCurrentRoomSettings(currentRoom);
                roomHelper.deSelect(_mapGraphics, _eleMap.getLevel(),  _eleMap.getShowUpper(), _eleMap.getShowLower());
                exitHelper.setExit(new Exit(r, currentRoom, _eleMap.getExitNumber(), (up?level+1:level), _mapGraphics));
                _eleMap.getExits().add(exitHelper.getExit());
                _eleMap.incrementExitNumber();
                updateExitSettings(exitHelper.getExit());
            }
        }
    }

    private void selectVerticalExit(int direction) {
        exitHelper.setExit(roomHelper.getRoom().getExit(direction));
        updateCurrentRoom(null, false);
        clearExitSettings();
        if (exitHelper.hasExit()) {
            exitHelper.getExit().select(_mapGraphics);
            updateExitSettings(exitHelper.getExit());
        }
    }

    private void deleteVerticalExit(int direction) {
        exitHelper.setExit(roomHelper.getRoom().getExit(direction));
        updateCurrentRoom(null, false);
        if (exitHelper.hasExit()) {
            _eleMap.getExits().remove(exitHelper.getExit());
            exitHelper.clearExit();
        }
    }

    private EleHashtable findSense() {
        return roomHelper.currentSense(optSmells.isSelected(), optSounds.isSelected());
    }

    private EleHashtable findSense(Room room) {
        return RoomHelper.currentSense(optSmells.isSelected(), optSounds.isSelected(), room);
    }

    private void updateItemList() {
        lstItems.setListData(roomHelper.getRoom().getItems().toArray());
    }

    private void updateDirectionList() {
        lstGuardDirections.setListData(roomHelper.getRoom().getExitDirections());
    }

    private void updateObjectLists() {
        ArrayList loadedObjects = roomHelper.getRoom().getLoadedObjects();

        lstObjects.setListData(loadedObjects.toArray());
        // todo JPC This needs to seperate out the guards from non-guards
        lstAvailableGuardObjects.setListData(loadedObjects.toArray());
    }

    private void updateFunctionLists() {
        ArrayList functions = roomHelper.getRoom().getFunctions();
        Iterator iter;
        Object key;

        lstFunctions.setListData(functions.toArray());

        // Now update the other lists of functions
        selGuardCheckFunctions.removeAllItems();
        selGuardMessageFunctions.removeAllItems();
        selGuardWizardFunctions.removeAllItems();
        iter = functions.iterator();
        while (iter.hasNext()) {
            key = iter.next();
            selGuardCheckFunctions.addItem(key);
            selGuardMessageFunctions.addItem(key);
            selGuardWizardFunctions.addItem(key);
        }
    }

    private void updateSenseList(EleHashtable senses) {
        Vector<String> v = new Vector<String>();

        if (senses != null) {
            v = new Vector<String>(senses.keySet());
        }
        lstSenses.setListData(v);
    }

    private void saveSenses(EleHashtable senses) {
        String default1 = txtDefaultSense.getText().trim();
        String default2 = txtVisibleDefaultSense.getText().trim();

        addSense(senses);

        if (default1.length() > 0) {
            senses.put(Room.DEFAULT_SENSE, default1);
        }
        if (default2.length() > 0) {
            senses.put(Room.VISIBLE_DEFAULT_SENSE, default2);
        }
    }

    private void loadSenses(EleHashtable senses) {
        Object tmp;

        if (senses != null) {
            tmp = senses.get(Room.DEFAULT_SENSE);
            if (tmp == null) tmp = "";
            txtDefaultSense.setText(tmp.toString());

            tmp = senses.get(Room.VISIBLE_DEFAULT_SENSE);
            if (tmp == null) tmp = "";
            txtVisibleDefaultSense.setText(tmp.toString());

            senses.remove(Room.VISIBLE_DEFAULT_SENSE);
            senses.remove(Room.DEFAULT_SENSE);

            updateSenseList(senses);
        }
    }

    private void addSense(EleHashtable senses) {
        if (senses != null) {
            senses.putString(txtSenseName.getText().trim(), txtSenseDescription.getText().trim());
            updateSenseList(senses);
        }

        txtSenseName.setText("");
        txtSenseDescription.setText("");
    }

    //----------------------------------------------------------
    // Private Class
    //----------------------------------------------------------

    private class ChangeLookAndFeelAction extends AbstractAction {
        private EleFrame frame;
        private String laf;

        protected ChangeLookAndFeelAction(EleFrame frame, String laf) {
            super("ChangeTheme");
            this.frame = frame;
            this.laf = laf;
        }

        public void actionPerformed(ActionEvent e) {
            frame.setLookAndFeel(laf);
        }
    }

} //end of class


