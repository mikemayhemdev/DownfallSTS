package charbosses.cards.blue;

import charbosses.actions.orb.EnemyChannelAction;
import charbosses.actions.unique.EnemyDarkImpulseAction;
import charbosses.cards.AbstractBossCard;
import charbosses.orbs.EnemyDark;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Dark;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

import java.util.ArrayList;

public class EnDoomAndGloom extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Doom and Gloom";
    private static final CardStrings cardStrings;

    public EnDoomAndGloom() {
        super(ID, cardStrings.NAME, "blue/attack/doom_and_gloom", 2, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY, AbstractMonster.Intent.ATTACK_BUFF);
        this.showEvokeValue = true;
        this.showEvokeOrbCount = 1;
        this.baseDamage = 10;
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new SFXAction("ATTACK_HEAVY"));
        this.addToBot(new VFXAction(p, new CleaveEffect(), 0.1F));
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
        this.addToBot(new EnemyChannelAction(new EnemyDark()));

    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(4);
        }

    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return 10;
    }

    public AbstractCard makeCopy() {
        return new EnDoomAndGloom();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Doom and Gloom");
    }
}
