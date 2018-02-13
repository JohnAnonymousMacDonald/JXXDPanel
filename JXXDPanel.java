/**
 *  JXXDPanel
 */

import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;



/**
 * @author John Anonymous MacDonald
 *
 */
public class XXDPanel extends JPanel
{
  private static final long serialVersionUID = 0x0000;

  private byte[] model;
  
  private JTextArea output;
  private JScrollPane sp;
 
  public XXDPanel()
  {
    super();
    this.setLayout(new BorderLayout());
    // TODO Convert this to use a FontMetric
    this.setPreferredSize(new Dimension(400, 300));

    output = new JTextArea();
    output.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
    output.setEditable(false);
    output.setLineWrap(false);
    output.setName("xxdPanel");
    output.setText("xxdPanel");

    sp = new JScrollPane(output);
    this.add(sp);


  }
  
  public XXDPanel(int fontSize)
  {
    this();
    output.setFont(new Font(Font.MONOSPACED, Font.PLAIN, fontSize));
  }
  
  public void setData(byte[] data)
  {
    model = data;
    updateOutput();
  }
  
  private void updateOutput()
  {
    /* 
     * The display should be 66 characters wide
     * 
     * 7 chars for the address
     * 2 chars for the colon and space
     * 40 chars for the hex display
     * 1 char for space (resulting in two in a row)
     * 16 chars for the ascii representation
     * 
     */
    StringBuffer pos, hex, ascii;
    StringBuffer buff = new StringBuffer();
    boolean space = false;
    int cp = 0;
	
    if (model != null)
      for (int i = 0; i <= model.length / 16; i++)
      {
        pos = new StringBuffer(String.format("%06x0: ", i));
        hex = new StringBuffer();
        ascii = new StringBuffer();

        for (int j = 0; j < 16; j++)
        {
          if (cp < model.length)
            processByte(model[cp], hex, ascii);
          else
            padd(hex, ascii);
          cp++;
          if (space)
            hex.append(' ');
            space = !space;
        }
        buff.append(pos);
        buff.append(hex);
        buff.append(" " + ascii);
        buff.append(System.getProperty("line.separator"));
      }
      else
        buff = new StringBuffer("0000000:");
    output.setText(buff.toString());
  }
  
  
  private void processByte(byte data, StringBuffer hex, StringBuffer ascii)
  {
    hex.append(String.format("%02x", data));

    if (data < 0x1f || data == 0x7f)
      ascii.append('.');
    else
      ascii.append((char)data);
  }
  
  private void padd(StringBuffer hex, StringBuffer ascii)
  {
    hex.append("  ");  // hex needs 2 spaces
    ascii.append(' ');
  }  
}


