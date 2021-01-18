package champ.cards;

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

public class StanceDance extends AbstractChampCard implements OctopusCard {

    public final static String ID = makeID("StanceDance");

    //stupid intellij stuff SKILL, SELF, UNCOMMON

    public StanceDance() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        tags.add(ChampMod.OPENER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //if (upgraded) techique();
        atb(new OctoChoiceAction(m, this));

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
                if (upgraded || AbstractDungeon.player.stance.ID.equals(BerserkerStance.STANCE_ID)|| AbstractDungeon.player.stance.ID.equals(champ.stances.UltimateStance.STANCE_ID)) {
                    ArrayList<AbstractCard> rCardList = new ArrayList<AbstractCard>();
                    for (AbstractCard t : CardLibrary.getAllCards()) {
                        if (!UnlockTracker.isCardLocked(t.cardID) && t.hasTag(ChampMod.COMBOBERSERKER))
                            rCardList.add(t);
                    }
                    AbstractCard r = rCardList.get(AbstractDungeon.cardRandomRng.random(rCardList.size() - 1));
                    UnlockTracker.markCardAsSeen(r.cardID);
                    makeInHand(r);
                }
                berserkOpen();
                break;
            case "octo:OctoDefense":
                if (upgraded || AbstractDungeon.player.stance.ID.equals(DefensiveStance.STANCE_ID) || AbstractDungeon.player.stance.ID.equals(UltimateStance.STANCE_ID)) {
                    ArrayList<AbstractCard> rCardList = new ArrayList<AbstractCard>();
                    for (AbstractCard t : CardLibrary.getAllCards()) {
                        if (!UnlockTracker.isCardLocked(t.cardID) && t.hasTag(ChampMod.COMBODEFENSIVE))
                            rCardList.add(t);
                    }
                    AbstractCard r = rCardList.get(AbstractDungeon.cardRandomRng.random(rCardList.size() - 1));
                    UnlockTracker.markCardAsSeen(r.cardID);
                    makeInHand(r);
                }
                defenseOpen();
                break;
        }

        AbstractDungeon.player.useJumpAnimation();
        atb(new VFXAction(new StanceDanceEffect(AbstractDungeon.player, false, true, false), 0.7F));

    }

    public void upp() {
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}