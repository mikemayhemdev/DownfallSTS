package collector.cards.CollectorCards.Attacks;

import collector.CollectorChar;
import collector.actions.AddAggroAction;
import collector.cards.CollectorCards.AbstractCollectorCard;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Bonfire extends AbstractCollectorCard {
    public final static String ID = makeID("Bonfire");

    public Bonfire() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY, CollectorCardSource.TORCH_HEAD);
        douBaseDamage =  baseDamage = damage = 4;
        douBlock = douBaseBlock = 8;
        magicNumber = baseMagicNumber = 1;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (CollectorChar.getLivingTorchHead() != null) {
            atb(new DamageAllEnemiesAction(CollectorChar.torch, douBaseDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.FIRE));
            atb(new SelectCardsInHandAction(magicNumber, ExhaustAction.TEXT[0], true, true, card -> true, Cards -> {
                if (Cards.size() > 0) {
                    atb(new ExhaustSpecificCardAction(Cards.get(0), AbstractDungeon.player.hand));
                    atb(new GainBlockAction(CollectorChar.torch, douBlock));
                    atb(new AddAggroAction(magicNumber));
                }
            }));
        }
    }

    @Override
    public void upp() {
        upgradeDamage(5);
    }
}