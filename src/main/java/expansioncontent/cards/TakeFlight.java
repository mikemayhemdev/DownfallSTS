package expansioncontent.cards;

import awakenedOne.cards.AbstractAwakenedCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.RegenPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import expansioncontent.expansionContentMod;

public class TakeFlight extends AbstractExpansionCard {
    public static final String ID = makeID("TakeFlight");

    private static final int BLOCK = 14;

    private static final int UPGRADE_BLOCK = 5;

    private static final int MAGIC = 1;

    private boolean chant = false;

    public TakeFlight() {
        super(ID, 1, AbstractCard.CardType.POWER, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);
        this.tags.add(expansionContentMod.STUDY);
        this.tags.add(expansionContentMod.STUDY_AWAKENEDONE);
        setBackgroundTexture("expansioncontentResources/images/512/bg_boss_awakenedone.png", "expansioncontentResources/images/1024/bg_boss_awakenedone.png");
        this.baseMagicNumber = this.magicNumber = 3;
        this.baseDownfallMagic = this.downfallMagic = 4;
        tags.add(CardTags.HEALING);
        expansionContentMod.loadJokeCardImage((AbstractCard)this, "TakeFlight.png");
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!this.chant)
            addToBot((AbstractGameAction) new ApplyPowerAction((AbstractCreature) AbstractDungeon.player, (AbstractCreature) AbstractDungeon.player, (AbstractPower) new RegenPower((AbstractCreature) AbstractDungeon.player, this.magicNumber), this.magicNumber));
        if (this.chant) {
            addToBot((AbstractGameAction) new ApplyPowerAction((AbstractCreature) AbstractDungeon.player, (AbstractCreature) AbstractDungeon.player, (AbstractPower) new RegenPower((AbstractCreature) AbstractDungeon.player, this.downfallMagic), this.downfallMagic));
            AbstractAwakenedCard.checkOnChant();
        }
    }

    public void triggerWhenDrawn() {
        this.chant = false;
    }

    public void triggerOnCardPlayed(AbstractCard card) {
        if (card.type == AbstractCard.CardType.POWER && AbstractDungeon.player.hand.contains((AbstractCard)this))
            this.chant = true;
    }

    @Override
    public void onMoveToDiscard() {
        this.chant = false;
    }

    public void triggerOnGlowCheck() {
        this.glowColor = this.chant ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            upgradeDownfall(1);
        }
    }
}
