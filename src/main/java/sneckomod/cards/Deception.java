package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.purple.Wallop;
import com.megacrit.cardcrawl.cards.red.Shockwave;
import com.megacrit.cardcrawl.cards.tempCards.Expunger;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import hermit.cards.AbstractDynamicCard;
import hermit.util.Wiz;
import sneckomod.SneckoMod;

import static sneckomod.SneckoMod.makeID;

public class Deception extends AbstractSneckoCard {

    public final static String ID = makeID("Deception");

    //stupid intellij stuff SKILL, SELF, COMMON

    //this is definitely one of the cards of all time - blue

    private static final int BLOCK = 14;
    private static final int UPG_BLOCK = 4;

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public Deception() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = BLOCK;
        SneckoMod.loadJokeCardImage(this, "Deception.png");
        this.cardsToPreview = new Shockwave();
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        addToBot(new MakeTempCardInHandAction(new Shockwave(), 1));
        }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPG_BLOCK);
            upgradeMagicNumber(UPG_MAGIC);
        }
    }
}