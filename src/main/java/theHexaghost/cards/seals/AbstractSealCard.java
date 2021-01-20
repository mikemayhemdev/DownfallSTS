package theHexaghost.cards.seals;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.RepairPower;
import theHexaghost.cards.AbstractHexaCard;
import theHexaghost.powers.RemoveMeBabey;
import theHexaghost.relics.TheBrokenSeal;
import theHexaghost.vfx.BrokenSealEffect;

import java.util.ArrayList;

public abstract class AbstractSealCard extends AbstractHexaCard {
    public AbstractSealCard(final String id, final int cost, final CardType type, final CardRarity rarity, final CardTarget target) {
        super(id, cost, type, rarity, target);
        tags.add(CardTags.HEALING);
        isEthereal = true;
    }

    public boolean canUpgrade() {
        return false;
    }

    public void upgrade() {
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

        realUse(abstractPlayer, abstractMonster);
        if (!AbstractDungeon.player.hasRelic(TheBrokenSeal.ID)) {
            ArrayList<String> sealList = new ArrayList<>();
            for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat) {
                if (c instanceof AbstractSealCard) {
                    if (!(sealList.contains(c.cardID))) {
                        sealList.add(c.cardID);
                    }
                }
            }
            if (playedAll(sealList)) {
                ArrayList<String> notToRemoveList = new ArrayList<>();
                ArrayList<AbstractCard> removeList = new ArrayList<>();
                for (AbstractCard c : abstractPlayer.masterDeck.group) {
                    if (c instanceof AbstractSealCard && !notToRemoveList.contains(c.cardID)) {
                        notToRemoveList.add(c.cardID);
                        removeList.add(c);
                    }
                }
                AbstractDungeon.actionManager.cardsPlayedThisCombat.removeIf(c -> c instanceof AbstractSealCard);
                for (AbstractPower p : AbstractDungeon.player.powers) {
                    if (p instanceof RemoveMeBabey || p instanceof RepairPower) {
                        addToTop(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, p, 1));
                    }
                }

                abstractPlayer.masterDeck.group.removeIf(removeList::contains);
                addToTop(new VFXAction(new BrokenSealEffect()));
            }
        }
    }


    public static boolean playedAll(ArrayList<String> sList) {
        return (sList.contains(FirstSeal.ID) && sList.contains(SecondSeal.ID) && sList.contains(ThirdSeal.ID) && sList.contains(FourthSeal.ID) && sList.contains(FifthSeal.ID) && sList.contains(SixthSeal.ID));
    }

    public abstract void realUse(AbstractPlayer p, AbstractMonster m);
}
