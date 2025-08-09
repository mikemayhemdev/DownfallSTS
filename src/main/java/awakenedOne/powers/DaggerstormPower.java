package awakenedOne.powers;

import awakenedOne.util.onGenerateCardMidcombatInterface;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.ThrowDaggerEffect;
import gremlin.actions.PseudoDamageRandomEnemyAction;
import hermit.util.Wiz;

public class DaggerstormPower extends AbstractAwakenedPower implements onGenerateCardMidcombatInterface {
    public static final String NAME = DaggerstormPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    public DaggerstormPower(int amount) {
        super(NAME, PowerType.BUFF, false, AbstractDungeon.player, null, amount);

    }


    @Override
    public void onCreateCard(AbstractCard card) {

        AbstractMonster m = AbstractDungeon.getMonsters().getRandomMonster(true);
        if (m != null) {
            this.addToBot(new VFXAction(new ThrowDaggerEffect(m.hb.cX, m.hb.cY)));
            AbstractDungeon.actionManager.addToBottom(new PseudoDamageRandomEnemyAction(m, new DamageInfo(AbstractDungeon.player, amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
    }

    @Override
    public void updateDescription() {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
