package org.telluriumsource.module

import org.telluriumsource.dsl.DslContext

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jan 6, 2010
 * 
 */

public class GoogleBookModule extends DslContext {

  public void defineUi() {
    ui.Container(uid: "GoogleBooksList", clocator: [tag: "table", id: "hp_table"]) {
      List(uid: "subcategory", clocator: [tag: "td", class: "sidebar"], separator: "div") {
        Container(uid: "{all} as category") {
          TextBox(uid: "title", clocator: [tag: "div", class: "sub_cat_title"])
          List(uid: "links", separator: "p") {
            UrlLink(uid: "{all} as text", clocator: [:])
          }
        }
      }
    }
  }

    public static String HTML_BODY ="""
<table id="hp_table" cellspacing="0" cellpadding="0">
  <tbody>
    <tr>
     <td class="sidebar" valign="top">
     <DIV class="sub_cat_section">
        <DIV class="sub_cat_title" style="">Fiction</DIV>
        <P><A href="http://books.google.com/books?q=+subject:%22+Literature+%22&amp;as_brr=3&amp;rview=1&amp;source=gbs_hplp_fict" style="">Literature</A></P>

        <P><A href="http://books.google.com/books?q=+subject:%22+Science+Fiction+%22&amp;as_brr=3&amp;rview=1&amp;source=gbs_hplp_fict" style="">Science fiction</A></P>

        <P><A href="http://books.google.com/books?q=+subject:%22+Fantasy+%22&amp;as_brr=3&amp;rview=1&amp;source=gbs_hplp_fict" style="">Fantasy</A></P>

        <P><A href="http://books.google.com/books?q=+subject:%22+Romance+%22&amp;as_brr=3&amp;rview=1&amp;source=gbs_hplp_fict" style="">Romance</A></P>

        <P><A href="http://books.google.com/books?q=+subject:%22+Fiction+/+Mystery+%26+detective+/+General+%22&amp;as_brr=3&amp;rview=1&amp;source=gbs_hplp_fict" style="">Mystery</A></P>

        <P><A href="http://books.google.com/books?q=+subject:%22+Fairy+tales+%22&amp;as_brr=3&amp;rview=1&amp;source=gbs_hplp_fict" style="">Fairy tales</A></P>

        <P><A href="http://books.google.com/books?q=+subject:%22+Short+Stories+%22&amp;as_brr=3&amp;rview=1&amp;source=gbs_hplp_fict" style="">Short stories</A></P>

        <P><A href="http://books.google.com/books?q=+subject:%22+Poetry+%22&amp;as_brr=3&amp;rview=1&amp;source=gbs_hplp_fict" style="">Poetry</A></P></DIV>
    <DIV class="sub_cat_section">
        <DIV class="sub_cat_title">Non-fiction</DIV>
        <P><A href="http://books.google.com/books?q=+subject:%22+Philosophy+%22&amp;as_brr=3&amp;rview=1&amp;source=gbs_hplp_nofict">Philosophy</A>
        </P>

        <P><A href="http://books.google.com/books?q=+subject:%22+Economics+%22&amp;as_brr=3&amp;rview=1&amp;source=gbs_hplp_nofict">Economics</A>
        </P>

        <P><A href="http://books.google.com/books?q=+subject:%22+Political+Science+%22&amp;as_brr=3&amp;rview=1&amp;source=gbs_hplp_nofict">Political science</A></P>

        <P><A href="http://books.google.com/books?q=+subject:%22+Linguistics+%22&amp;as_brr=3&amp;rview=1&amp;source=gbs_hplp_nofict">Linguistics</A>
        </P>

        <P><A href="http://books.google.com/books?q=+subject:%22+Mathematics+%22&amp;as_brr=3&amp;rview=1&amp;source=gbs_hplp_nofict">Mathematics</A>
        </P>

        <P><A href="http://books.google.com/books?q=+subject:%22+Science+/+Physics+%22&amp;as_brr=3&amp;rview=1&amp;source=gbs_hplp_nofict">Physics</A>
        </P>

        <P><A href="http://books.google.com/books?q=+subject:%22+Science+/+Chemistry+/+General+%22&amp;as_brr=3&amp;rview=1&amp;source=gbs_hplp_nofict">Chemistry</A>
        </P>

        <P><A href="http://books.google.com/books?q=+subject:%22+Science+/+Life+Sciences+/+Biology+/+General+%22&amp;as_brr=3&amp;rview=1&amp;source=gbs_hplp_nofict">Biology</A>
        </P></DIV>
    <DIV class="sub_cat_section">
        <DIV class="sub_cat_title">Random subjects</DIV>
        <P><A href="http://books.google.com/books?q=+subject:%22+Toys+%22&amp;as_brr=3&amp;rview=1&amp;source=gbs_hplp_subj">Toys</A>
        </P>

        <P><A href="http://books.google.com/books?q=+subject:%22+Solar+houses+%22&amp;as_brr=3&amp;rview=1&amp;source=gbs_hplp_subj">Solar houses</A></P>

        <P><A href="http://books.google.com/books?q=+subject:%22+Stories+in+rhyme+%22&amp;as_brr=3&amp;rview=1&amp;source=gbs_hplp_subj">Stories in rhyme</A></P>

        <P><A href="http://books.google.com/books?q=+subject:%22+Courtship+%22&amp;as_brr=3&amp;rview=1&amp;source=gbs_hplp_subj">Courtship</A>
        </P>

        <P><A href="http://books.google.com/books?q=+subject:%22+Mythology+%22&amp;as_brr=3&amp;rview=1&amp;source=gbs_hplp_subj">Mythology</A>
        </P>

        <P><A href="http://books.google.com/books?q=+subject:%22+Differential+equations+%22&amp;as_brr=3&amp;rview=1&amp;source=gbs_hplp_subj">Differential equations</A></P>

        <P><A href="http://books.google.com/books?q=+subject:%22+Inscriptions,+Latin+%22&amp;as_brr=3&amp;rview=1&amp;source=gbs_hplp_subj">Latin inscriptions</A></P>

        <P><A href="http://books.google.com/books?q=+subject:%22+Nuclear+energy+%22&amp;as_brr=3&amp;rview=1&amp;source=gbs_hplp_subj">Nuclear energy</A></P>
    </DIV>
</td>
</tr>
</tbody>
</table>
    """
}