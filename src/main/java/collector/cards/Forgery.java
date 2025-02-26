package collector.cards;

import collector.CollectorCollection;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.SelectCardsCenteredAction;
import sneckomod.SneckoMod;

import java.util.ArrayList;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.att;

public class Forgery extends AbstractCollectorCard {
    public final static String ID = makeID(Forgery.class.getSimpleName());
    // intellij stuff attack, enemy, common, 9, 3, , , , 

    public Forgery() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 8;
        baseMagicNumber = magicNumber = 2;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        if (!CollectorCollection.combatCollection.isEmpty()) {
            ArrayList<AbstractCard> possibilities = new ArrayList<>();
            ArrayList<AbstractCard> toShow = new ArrayList<>();
            possibilities.addAll(CollectorCollection.combatCollection.group);
            for (int i = 0; i < Math.min(CollectorCollection.combatCollection.size(), magicNumber); i++) {
                toShow.add(possibilities.remove(AbstractDungeon.cardRandomRng.random(possibilities.size() - 1)));
            }
            addToBot(new SelectCardsCenteredAction(toShow, cardStrings.EXTENDED_DESCRIPTION[0], (cards) -> {
                att(new MakeTempCardInHandAction(cards.get(0), true));
            }));
        }
    }

    public void upp() {
        upgradeDamage(2);
        upgradeMagicNumber(1);
    }
}