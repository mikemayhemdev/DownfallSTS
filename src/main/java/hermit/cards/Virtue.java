package hermit.cards;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.HealEffect;
import com.megacrit.cardcrawl.vfx.combat.HealVerticalLineEffect;
import hermit.HermitMod;
import hermit.actions.ReduceDebuffsAction;
import hermit.actions.ReprieveAction;
import hermit.characters.hermit;

import static hermit.HermitMod.loadJokeCardImage;
import static hermit.HermitMod.makeCardPath;

public class Virtue extends AbstractDynamicCard {


    /*
     * SNAPSHOT: Deals 12/16 damage, Dead-On makes it free.
     */


    // TEXT DECLARATION

    public static final String ID = HermitMod.makeID(Virtue.class.getSimpleName());
    public static final String IMG = makeCardPath("virtue.png");

    // /TEXT DECLARATION/

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = hermit.Enums.COLOR_YELLOW;

    private static final int COST = 1;


    // /STAT DECLARATION/

    public Virtue() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        loadJokeCardImage(this, "virtue.png");

        magicNumber = baseMagicNumber = 2;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        int roll = MathUtils.random(0, 2);
        if (roll == 0) {
            CardCrawlGame.sound.play("HEAL_1");
        } else if (roll == 1) {
            CardCrawlGame.sound.play("HEAL_2");
        } else {
            CardCrawlGame.sound.play("HEAL_3");
        }

        float    X_JITTER = 120.0F * Settings.scale;
        float    Y_JITTER = 120.0F * Settings.scale;
        float    OFFSET_Y = -50.0F * Settings.scale;

        for(int i = 0; i < 18; ++i) {
            AbstractDungeon.effectsQueue.add(new HealVerticalLineEffect((p.hb.cX - p.animX) + MathUtils.random(-X_JITTER * 1.5F, X_JITTER * 1.5F),  p.hb.cY + OFFSET_Y + MathUtils.random(-Y_JITTER, Y_JITTER)));
        }
        this.addToBot(new ReduceDebuffsAction(AbstractDungeon.player, magicNumber));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
            initializeDescription();
        }
    }
}