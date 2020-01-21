package theHexaghost.cards.seals;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.cards.AbstractHexaCard;
import theHexaghost.relics.TheBrokenSeal;

import java.util.ArrayList;

public abstract class AbstractSealCard extends AbstractHexaCard {
    public AbstractSealCard(final String id, final int cost, final CardType type, final CardRarity rarity, final CardTarget target) {
        super(id, cost, type, rarity, target);
        tags.add(CardTags.HEALING);
    }

    public boolean canUpgrade() {
        return false;
    }

    public void upgrade() {
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        ArrayList<String> sealList = new ArrayList<>();
        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat) {
            if (c instanceof AbstractSealCard) {
                if (!(sealList.contains(c.cardID))) {
                    sealList.add(c.cardID);
                }
            }
        }
        if (sealList.size() == 6) {
            for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                if (c instanceof AbstractSealCard) {
                    AbstractDungeon.player.masterDeck.removeCard(c);
                }
            }
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2F, Settings.HEIGHT / 2F, new TheBrokenSeal());
        }
        realUse(abstractPlayer, abstractMonster);
    }

    public abstract void realUse(AbstractPlayer p, AbstractMonster m);
}
