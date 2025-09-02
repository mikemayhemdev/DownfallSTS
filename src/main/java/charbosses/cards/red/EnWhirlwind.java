package charbosses.cards.red;

import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;
import downfall.downfallMod;

public class EnWhirlwind extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Whirlwind";
    private static final CardStrings cardStrings;
    private int cost;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Whirlwind");
    }

    public EnWhirlwind(int inCost) {
        super(ID, EnWhirlwind.cardStrings.NAME, "red/attack/whirlwind", -1, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.RED, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        this.baseDamage = 5;
        this.tags.add(downfallMod.CHARBOSS_ATTACK);
        cost = inCost;
    }
    public EnWhirlwind() {
        this(0);
    }


    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {

        this.addToBot(new SFXAction("ATTACK_WHIRLWIND"));
        this.addToBot(new VFXAction(new WhirlwindEffect(), 0.0F));
        for (int i = 0; i < cost; ++i) {
            this.addToBot(new VFXAction(p, new CleaveEffect(true), 0.0F));
            this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }
        //this.addToBot(new EnemyDiscardPileToTopOfDeckAction((AbstractCharBoss) m));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(3);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnWhirlwind();
    }
}
