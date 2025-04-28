package collector.cards.collectibles;

import collector.CollectorMod;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import sneckomod.SneckoMod;

import static collector.CollectorMod.makeID;
import static collector.CollectorMod.slaverTrioCheck;
import static collector.util.Wiz.applyToEnemy;

public class RedSlaverCard extends AbstractCollectibleCard {
    public final static String ID = makeID(RedSlaverCard.class.getSimpleName());
    // intellij stuff skill, enemy, common, , , , , 9, 5

    public RedSlaverCard() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 9;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToEnemy(m, new StrengthPower(m, -magicNumber));
        if (!m.hasPower(ArtifactPower.POWER_ID)) {
            applyToEnemy(m, new GainStrengthPower(m, magicNumber));
        }

        CollectorMod.redPlayedThisCombat = true;
        slaverTrioCheck();
    }

    public void upp() {
        upgradeMagicNumber(5);
    }



    @Override
    public void triggerOnGlowCheck() {
        if (CollectorMod.redPlayedThisCombat && CollectorMod.taskPlayedThisCombat && !CollectorMod.bluePlayedThisCombat) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR;
            return;
        }
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR;
    }
}