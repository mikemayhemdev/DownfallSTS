package champ.cards;

import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import champ.ChampMod;
import champ.stances.BerserkerStance;
import champ.stances.DefensiveStance;
import champ.stances.UltimateStance;
import champ.vfx.StanceDanceEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import downfall.actions.OctoChoiceAction;
import downfall.cards.OctoChoiceCard;
import downfall.util.OctopusCard;

import java.util.ArrayList;

import static champ.ChampMod.loadJokeCardImage;
@NoCompendium
public class StanceDanceCrown extends AbstractChampCard implements OctopusCard {

    public final static String ID = makeID("StanceDanceCrown");

    //stupid intellij stuff SKILL, SELF, UNCOMMON

    public StanceDanceCrown() {
        super(ID, 0, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF);
        tags.add(ChampMod.OPENER);
        loadJokeCardImage(this, "StanceDance.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new OctoChoiceAction(m, this));
        postInit();
    }

    public ArrayList<OctoChoiceCard> choiceList() {
        ArrayList<OctoChoiceCard> cardList = new ArrayList<>();
        cardList.add(new OctoChoiceCard("octo:OctoBerserk", this.name, ChampMod.makeCardPath("OctoStanceBerserker.png"), this.EXTENDED_DESCRIPTION[0]));
        cardList.add(new OctoChoiceCard("octo:OctoDefense", this.name, ChampMod.makeCardPath("OctoStanceDefensive.png"), this.EXTENDED_DESCRIPTION[1]));
        return cardList;
    }

    public void doChoiceStuff(AbstractMonster m, OctoChoiceCard card) {
        switch (card.cardID) {
            case "octo:OctoBerserk":
                ChampMod.berserkOpen();
                BerserkerStance bs = new BerserkerStance();
               // bs.techique();
               // if (upgraded) bs.techique();
                break;
            case "octo:OctoDefense":
                ChampMod.defenseOpen();
                DefensiveStance ds = new DefensiveStance();
             //   ds.techique();
              //  if (upgraded) ds.techique();
                break;
        }

     //   AbstractDungeon.player.useJumpAnimation();
      //  atb(new VFXAction(new StanceDanceEffect(AbstractDungeon.player, false, true, false), 0.7F));

    }

    public void upp() {
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}