package hermit.cards;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.tempCards.ThroughViolence;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import com.megacrit.cardcrawl.vfx.combat.ViolentAttackEffect;
import hermit.HermitMod;
import hermit.characters.hermit;
import hermit.powers.Bruise;
import hermit.powers.Drained;

import java.util.Iterator;

import static hermit.HermitMod.loadJokeCardImage;
import static hermit.HermitMod.makeCardPath;

public class NoHoldsBarred extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = HermitMod.makeID(NoHoldsBarred.class.getSimpleName());
    public static final String IMG = makeCardPath("no_holds_barred.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // /TEXT DECLARATION/

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = hermit.Enums.COLOR_YELLOW;

    private static final int COST = 2;


    // /STAT DECLARATION/


    public NoHoldsBarred() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        isMultiDamage=true;
        baseDamage=18;
        magicNumber = baseMagicNumber = 4;
        loadJokeCardImage(this, "no_holds_barred.png");
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        Iterator var3 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();


        while(var3.hasNext()) {
            AbstractMonster mo = (AbstractMonster)var3.next();

            if (!mo.isDead)
            {
                this.addToBot(new VFXAction(new ViolentAttackEffect(mo.hb.cX, mo.hb.cY, Color.YELLOW)));
            }
        }

        this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));

        Iterator var4 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();

        while(var4.hasNext()) {
            AbstractMonster mo = (AbstractMonster)var4.next();
            this.addToBot(new ApplyPowerAction(mo, p, new Bruise(mo, magicNumber), magicNumber, true, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        }

        this.addToBot(new ApplyPowerAction(p, p, new Drained(p,p, 1), 1));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(4);
            upgradeMagicNumber(1);
            initializeDescription();
        }
    }
}
