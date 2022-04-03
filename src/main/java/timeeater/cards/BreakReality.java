package timeeater.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.IronWave;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeeater.actions.SuspendAction;

import static timeeater.TimeEaterMod.makeID;

public class BreakReality extends AbstractTimeEaterCard {
    public final static String ID = makeID("BreakReality");
    // intellij stuff attack, self_and_enemy, rare, 5, 3, 5, 3, , 

    public BreakReality() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = 5;
        baseBlock = 5;
        cardsToPreview = getNoteCard();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
        addToBot(new SuspendAction(getNoteCard()));
    }

    public static AbstractCard getNoteCard() {
        AbstractCard obtainCard = CardLibrary.getCard(CardCrawlGame.playerPref.getString("NOTE_CARD", "Iron Wave"));
        if (obtainCard == null) {
            obtainCard = new IronWave();
        }

        obtainCard = obtainCard.makeCopy();

        for (int i = 0; i < CardCrawlGame.playerPref.getInteger("NOTE_UPGRADE", 0); ++i) {
            obtainCard.upgrade();
        }
        return obtainCard;
    }

    public void upp() {
        upgradeDamage(3);
        upgradeBlock(3);
    }
}