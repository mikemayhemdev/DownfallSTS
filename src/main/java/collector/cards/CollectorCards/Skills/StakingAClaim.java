package collector.cards.CollectorCards.Skills;

import collector.cards.CollectorCards.AbstractCollectorCard;
import collector.powers.SoulMark;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.CollectorCurseEffect;

public class StakingAClaim extends AbstractCollectorCard {
    public final static String ID = makeID("StakingAClaim");
    protected final CardStrings cardStrings;
    public StakingAClaim() {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.ENEMY);
        magicNumber = baseMagicNumber = 99;
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        name = cardStrings.NAME;
        originalName = NAME;
        rawDescription = DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_COLLECTOR_DEBUFF"));
        atb(new VFXAction(new CollectorCurseEffect(m.drawX,m.drawY),2.0f));
        applyToEnemy(m,new SoulMark(99,m));
        applyToEnemy(m, new WeakPower(m,99,false));
        applyToEnemy(m, new VulnerablePower(m,99,false));
    }

    @Override
    public void upp() {
        upgradeBaseCost(1);
    }
}