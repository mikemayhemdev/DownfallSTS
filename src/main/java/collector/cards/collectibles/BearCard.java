package collector.cards.collectibles;

import collector.CollectorMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import sneckomod.SneckoMod;

import static collector.CollectorMod.banditBoost;
import static collector.CollectorMod.makeID;
import static collector.util.Wiz.atb;
import static collector.util.Wiz.getEnemies;

public class BearCard extends AbstractCollectibleCard {
    public final static String ID = makeID(BearCard.class.getSimpleName());
    // intellij stuff attack, enemy, uncommon, 10, 3, 10, 3, , 

    public BearCard() {
        super(ID, 2, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY);
       // baseDamage = 10;
        baseBlock = 10;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        this.baseDamage = p.currentBlock;
        this.calculateCardDamage(m);
        dmg(m, AbstractGameAction.AttackEffect.SMASH);
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();

        if (CollectorMod.banditBoost(0)){
            atb(new GainEnergyAction(1));
        }
    }

    public void applyPowers() {
        this.baseDamage = AbstractDungeon.player.currentBlock;
        super.applyPowers();
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    public void calculateCardDamage(AbstractMonster mo) {
        baseDamage = AbstractDungeon.player.currentBlock;
        super.calculateCardDamage(mo);
        //this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    public void upp() {
        upgradeBlock(4);
    }

    @Override
    public void triggerOnGlowCheck() {
            if (banditBoost(0)) {
                this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR;
                return;
            }
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR;
    }
}