package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import hermit.cards.AbstractDynamicCard;
import hermit.util.Wiz;
import sneckomod.SneckoMod;

import static sneckomod.SneckoMod.makeID;

public class Deception extends AbstractSneckoCard {

    public final static String ID = makeID("Deception");

    //stupid intellij stuff SKILL, SELF, COMMON

    //this is definitely one of the cards of all time - blue

    private static final int BLOCK = 7;
    private static final int UPG_BLOCK = 2;

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public Deception() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
        this.tags.add(SneckoMod.OVERFLOW);
        SneckoMod.loadJokeCardImage(this, "Deception.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        for(AbstractMonster mo: Wiz.getEnemies()) {
            this.addToBot(new ApplyPowerAction(mo, p, new WeakPower(mo, this.magicNumber, false), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
        }
    }

    //Glowverflow - make the card glow if overflow is active~
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractDynamicCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (isOverflowActive()) {
            this.glowColor = AbstractDynamicCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPG_BLOCK);
            upgradeMagicNumber(UPG_MAGIC);
        }
    }
}