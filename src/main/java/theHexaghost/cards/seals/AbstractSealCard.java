package theHexaghost.cards.seals;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.cards.AbstractHexaCard;
import theHexaghost.relics.TheBrokenSeal;
import theHexaghost.vfx.BrokenSealText;

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
        ArrayList<AbstractCard> sealList = new ArrayList<>();
        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat) {
            if (c instanceof AbstractSealCard) {
                if (!(sealList.contains(c))) {
                    sealList.add(c);
                }
            }
        }
        if (sealList.size() == 6) {
            AbstractDungeon.player.masterDeck.group.removeIf(sealList::contains);
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2F, Settings.HEIGHT / 2F, new TheBrokenSeal());
            addToTop(new VFXAction(new BrokenSealText(Color.PURPLE.cpy(), TEXT[0], 5.5f)));
        }
        realUse(abstractPlayer, abstractMonster);
    }

    public abstract void realUse(AbstractPlayer p, AbstractMonster m);
}
