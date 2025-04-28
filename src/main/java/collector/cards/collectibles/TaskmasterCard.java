package collector.cards.collectibles;

import collector.CollectorMod;
import collector.powers.DoomPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import sneckomod.SneckoMod;

import static collector.CollectorMod.makeID;
import static collector.CollectorMod.slaverTrioCheck;
import static collector.util.Wiz.applyToEnemy;
import static collector.util.Wiz.applyToSelf;

public class TaskmasterCard extends AbstractCollectibleCard {
    public final static String ID = makeID(TaskmasterCard.class.getSimpleName());
    // intellij stuff attack, enemy, uncommon, 7, 2, , , 1, 1

    public TaskmasterCard() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 12;
        baseMagicNumber = magicNumber = 12;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToEnemy(m, new DoomPower(m, magicNumber));
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HEAVY);
        CollectorMod.taskPlayedThisCombat = true;
        slaverTrioCheck();
    }

    public void upp() {
        upgradeDamage(3);
        upgradeMagicNumber(3);
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