package champ.cards;

import basemod.helpers.CardModifierManager;
import champ.ChampMod;
import champ.util.RetainCardMod;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class ArenaPreparation extends AbstractChampCard {

    public final static String ID = makeID("ArenaPreparation");

    //stupid intellij stuff skill, self, uncommon

    public ArenaPreparation() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        myHpLossCost = 8;
        baseMagicNumber = magicNumber = 2;
        exhaust = true;
        tags.add(ChampMod.TECHNIQUE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        techique();
        fatigue(8);
        for (int i = 0; i < magicNumber; i++) {
            ArrayList<AbstractCard> qCardList = new ArrayList<AbstractCard>();
            for (AbstractCard t : CardLibrary.getAllCards()) {
                if (t.hasTag(ChampMod.TECHNIQUE)) qCardList.add(t);
            }
            AbstractCard c = qCardList.get(AbstractDungeon.cardRandomRng.random(qCardList.size() - 1));
            CardModifierManager.addModifier(c, new RetainCardMod());
            makeInHand(c);
        }
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}