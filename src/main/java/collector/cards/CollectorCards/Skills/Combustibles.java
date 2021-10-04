package collector.cards.CollectorCards.Skills;

import collector.CollectorChar;
import collector.cards.CollectorCards.AbstractCollectorCard;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Combustibles extends AbstractCollectorCard {
    public final static String ID = makeID("Combustibles");

    public Combustibles() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        douBaseBlock = block = baseBlock = 4;
        magicNumber = baseMagicNumber = 6;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (CollectorChar.isFrontDragon()) {
            atb(new GainBlockAction(CollectorChar.torch, douBlock));
            atb(new SelectCardsInHandAction(magicNumber, ExhaustAction.TEXT[0], true, true, card -> true, Cards -> {
                if (Cards.size() > 0) {
                    CardRarity rarity = Cards.get(0).rarity;
                    atb(new ExhaustSpecificCardAction(Cards.get(0), AbstractDungeon.player.hand));
                    if (rarity == CardRarity.COMMON || rarity == CardRarity.BASIC || rarity == CardRarity.CURSE || rarity == CardRarity.SPECIAL) {
                        atb(new GainBlockAction(CollectorChar.torch, douBlock));
                    } else if (rarity == CardRarity.UNCOMMON) {
                        atb(new GainBlockAction(CollectorChar.torch, douBlock + 8));
                    } else {
                        atb(new GainBlockAction(CollectorChar.torch, douBlock + 16));
                    }
                }
            }));
        } else {
            atb(new GainBlockAction(p, block));
            atb(new SelectCardsInHandAction(magicNumber, ExhaustAction.TEXT[0], true, true, card -> true, Cards -> {
                if (Cards.size() > 0) {
                    CardRarity rarity = Cards.get(0).rarity;
                    atb(new ExhaustSpecificCardAction(Cards.get(0), AbstractDungeon.player.hand));
                    if (rarity == CardRarity.COMMON || rarity == CardRarity.BASIC || rarity == CardRarity.CURSE || rarity == CardRarity.SPECIAL) {
                        atb(new GainBlockAction(p, block));
                    } else if (rarity == CardRarity.UNCOMMON) {
                        atb(new GainBlockAction(p, block + 8));
                    } else {
                        atb(new GainBlockAction(p, block + 16));
                    }
                }
            }));
        }
    }

    @Override
    public void upp() {
        upgradeBlock(3);
    }
}