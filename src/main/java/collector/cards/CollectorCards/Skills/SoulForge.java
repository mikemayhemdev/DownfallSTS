package collector.cards.CollectorCards.Skills;

import collector.CollectorChar;
import collector.cards.CollectorCards.AbstractCollectorCard;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

public class SoulForge extends AbstractCollectorCard {
    public final static String ID = makeID("SoulForge");

    public SoulForge() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        magicNumber = baseMagicNumber = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (CollectorChar.isFrontTorchHead()) {
            applyToSelf(new VigorPower(p, magicNumber));
            atb(new SelectCardsInHandAction(magicNumber, ExhaustAction.TEXT[0], true, true, card -> true, Cards -> {
                if (Cards.size() > 0) {
                    CardRarity rarity = Cards.get(0).rarity;
                    atb(new ExhaustSpecificCardAction(Cards.get(0), AbstractDungeon.player.hand));
                    if (rarity == CardRarity.COMMON || rarity == CardRarity.BASIC || rarity == CardRarity.CURSE || rarity == CardRarity.SPECIAL) {
                        applyToSelf(new VigorPower(p, 4));
                    } else if (rarity == CardRarity.UNCOMMON) {
                        applyToSelf(new VigorPower(p, 8));
                    } else {
                        applyToSelf(new VigorPower(p, 10));
                    }
                }
            }));
        } else {
            applyToSelf(new VigorPower(CollectorChar.torch, magicNumber));
            atb(new SelectCardsInHandAction(magicNumber, ExhaustAction.TEXT[0], true, true, card -> true, Cards -> {
                if (Cards.size() > 0) {
                    CardRarity rarity = Cards.get(0).rarity;
                    atb(new ExhaustSpecificCardAction(Cards.get(0), AbstractDungeon.player.hand));
                    if (rarity == CardRarity.COMMON || rarity == CardRarity.BASIC || rarity == CardRarity.CURSE || rarity == CardRarity.SPECIAL) {
                        applyToSelf(new VigorPower(CollectorChar.torch, 4));
                    } else if (rarity == CardRarity.UNCOMMON) {
                        applyToSelf(new VigorPower(CollectorChar.torch, 8));
                    } else {
                        applyToSelf(new VigorPower(CollectorChar.torch, 10));
                    }
                }
            }));
        }
    }

    @Override
    public void upp() {

    }
}