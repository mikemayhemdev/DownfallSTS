package hermit.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import hermit.HermitMod;
import hermit.actions.ReduceCostActionFixed;
import hermit.characters.hermit;
import hermit.effects.HermitUpgradeShineEffect;
import hermit.powers.MaintenanceStrikePower;

import static hermit.HermitMod.*;

public class Maintenance extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = HermitMod.makeID(Maintenance.class.getSimpleName());
    public static final String IMG = makeCardPath("maintenance.png");

    // /TEXT DECLARATION/

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = hermit.Enums.COLOR_YELLOW;

    private static final int COST = 1;

    // /STAT DECLARATION/

    public Maintenance() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = 3;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 1;
        loadJokeCardImage(this, "maintenance.png");
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.effectsQueue.add(new HermitUpgradeShineEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY));
        this.addToBot(new ApplyPowerAction(p, p, new MaintenanceStrikePower(p, this.magicNumber), this.magicNumber));
        this.addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, this.defaultBaseSecondMagicNumber), this.defaultBaseSecondMagicNumber));
        this.addToBot(new ReduceCostActionFixed(this.uuid, 1));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            initializeDescription();
            upgradeDefaultSecondMagicNumber(1);
            upgradeMagicNumber(1);
        }
    }
}