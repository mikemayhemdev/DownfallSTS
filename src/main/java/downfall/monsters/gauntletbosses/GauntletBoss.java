package downfall.monsters.gauntletbosses;

import com.megacrit.cardcrawl.cards.red.Strike_Red;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.monsters.NeowBoss;

public abstract class GauntletBoss extends AbstractMonster {

    public GauntletBoss(String name, String id, int maxHealth, float hb_x, float hb_y, float hb_w, float hb_h, String imgUrl, float offsetX, float offsetY) {
        super(name, id, maxHealth, hb_x, hb_y, hb_w, hb_h, imgUrl, offsetX, offsetY);
    }

    public String moveName(String ID){
        return moveName(ID, "");
    }
    public String moveName(String ID, String ID2){
        if (ID2 == ""){

            return CardLibrary.getCard(ID).name;
        } else {
            return CardLibrary.getCard(ID).name + " / " + CardLibrary.getCard(ID2).name;
        }
    }
}
