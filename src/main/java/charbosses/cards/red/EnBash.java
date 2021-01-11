package charbosses.cards.red;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.relics.CBR_ChampionsBelt;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
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
import downfall.downfallMod;

import java.util.ArrayList;

public class EnBash extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Bash";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Bash");
    }

    public EnBash() {
        super(ID, EnBash.cardStrings.NAME, "red/attack/bash", 2, EnBash.cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.RED, CardRarity.BASIC, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK_DEBUFF);
        this.baseDamage = 8;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(downfallMod.CHARBOSS_SETUP);
        artifactConsumedIfPlayed = 1;
        if (AbstractCharBoss.boss.hasRelic(CBR_ChampionsBelt.ID)){
            artifactConsumedIfPlayed = 2;
        }
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        this.addToBot(new ApplyPowerAction(p, m, new VulnerablePower(p, this.magicNumber + 1, false), this.magicNumber + 1));
        if (!p.hasPower(ArtifactPower.POWER_ID)){
            if (AbstractCharBoss.boss.hasRelic(CBR_ChampionsBelt.ID)){
                AbstractCharBoss.boss.getRelic(CBR_ChampionsBelt.ID).onTrigger();
            }
        }
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(2);
            this.upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnThunderclap();
    }

//    @Override
//    public int getPriority(ArrayList<AbstractCard> hand) {
//        return autoPriority() + 10;
//    }
}
