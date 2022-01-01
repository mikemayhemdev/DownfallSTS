package collector.cards.Collectibles;

import basemod.helpers.CardModifierManager;
import collector.Interfaces.PerpetualCard;
import collector.util.PlusDamageAndBlockCardMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class GremlinPoker extends AbstractCollectibleCard implements PerpetualCard {
    public final static String ID = makeID("GremlinPoker");
    int perpetualbonus;
    public GremlinPoker() {
        super(ID, 0, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        damage = baseDamage = 3;
        perpetualbonus = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
    }

    @Override
    public void upp() {
        CardModifierManager.addModifier(this, new PlusDamageAndBlockCardMod(perpetualbonus));
    }

    @Override
    public void PerpetualBonus() {
        baseDamage += perpetualbonus;
    }
}
