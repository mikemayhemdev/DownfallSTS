package charbosses.cards.red;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.relics.CBR_ChampionsBelt;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

import java.util.ArrayList;

public class EnThunderclap extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Thunderclap";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Thunderclap");
    }

    public EnThunderclap() {
        super(ID, cardStrings.NAME, "red/attack/thunder_clap", 1, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.RED, CardRarity.COMMON, CardTarget.ALL_ENEMY, AbstractMonster.Intent.ATTACK_DEBUFF);
        this.baseDamage = 4;
        baseMagicNumber = magicNumber = 1;
        artifactConsumedIfPlayed = 1;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new VFXAction(new LightningEffect(p.drawX, p.drawY), 0.05F));

        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.LIGHTNING));
        this.addToBot(new ApplyPowerAction(p, m, new VulnerablePower(p, this.magicNumber + 1, false), this.magicNumber + 1));
        if (!p.hasPower(ArtifactPower.POWER_ID)) {
            if (AbstractCharBoss.boss.hasRelic(CBR_ChampionsBelt.ID)) {
                AbstractCharBoss.boss.getRelic(CBR_ChampionsBelt.ID).onTrigger();
            }
        }
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
        return new EnThunderclap();
    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return autoPriority() + 10;
    }
}
