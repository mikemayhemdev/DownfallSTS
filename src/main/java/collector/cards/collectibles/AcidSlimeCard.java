package collector.cards.collectibles;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

import java.util.ArrayList;
import java.util.Arrays;

import static collector.CollectorMod.makeID;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.*;

public class AcidSlimeCard extends AbstractCollectibleCard {
    public final static String ID = makeID(AcidSlimeCard.class.getSimpleName());
    // intellij stuff skill, enemy, common, , , , , , 

    public AcidSlimeCard() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> list = new ArrayList();

        for (CardGroup cardGroup : Arrays.asList(srcCommonCardPool, srcUncommonCardPool, srcRareCardPool)) {
            for (AbstractCard c3 : cardGroup.group) {
                if (c3.cost == 0) {
                    list.add(c3);
                }
            }

        }

        for (int i = 0; i < magicNumber; i++)
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(list.get(cardRandomRng.random(list.size() - 1))));

    }


    public void upp() {
        upgradeMagicNumber(1);
        uDesc();
    }
}