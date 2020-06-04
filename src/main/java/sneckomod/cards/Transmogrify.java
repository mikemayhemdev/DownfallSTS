package sneckomod.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Circlet;
import sneckomod.vfx.AnnouncementEffect;

import java.util.ArrayList;

public class Transmogrify extends AbstractSneckoCard {

    public final static String ID = makeID("Transmogrify");

    //stupid intellij stuff SKILL, SELF, RARE

    public Transmogrify() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        exhaust = true;
        tags.add(CardTags.HEALING);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractRelic> eligibleRelicsList = new ArrayList<>(AbstractDungeon.player.relics);
        eligibleRelicsList.removeIf(c -> c.tier == AbstractRelic.RelicTier.SPECIAL);
        if (!eligibleRelicsList.isEmpty()) {
            AbstractRelic q = eligibleRelicsList.get(AbstractDungeon.cardRandomRng.random(eligibleRelicsList.size() - 1));
            q.flash();
            AbstractDungeon.player.loseRelic(q.relicId);
            AbstractRelic s = returnTrueRandomScreenlessRelic(q.tier);
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2F, Settings.HEIGHT / 2F, s);
            AbstractDungeon.effectsQueue.add(new AnnouncementEffect(Color.PURPLE.cpy(), q.name + " traded for " + s.name + "!!!", 5.5F));
        }
    }

    public static AbstractRelic returnTrueRandomScreenlessRelic(AbstractRelic.RelicTier tier) {
        ArrayList<AbstractRelic> eligibleRelicsList = new ArrayList<>();
        ArrayList<AbstractRelic> myGoodStuffList = new ArrayList<>();
        for (String r : AbstractDungeon.commonRelicPool) {
            eligibleRelicsList.add(RelicLibrary.getRelic(r));
        }
        for (String r : AbstractDungeon.uncommonRelicPool) {
            eligibleRelicsList.add(RelicLibrary.getRelic(r));
        }
        for (String r : AbstractDungeon.rareRelicPool) {
            eligibleRelicsList.add(RelicLibrary.getRelic(r));
        }
        eligibleRelicsList.addAll(RelicLibrary.starterList);
        try {
            for (AbstractRelic r : eligibleRelicsList)
                if (r.tier == tier && r.getClass().getMethod("onEquip").getDeclaringClass() == AbstractRelic.class && r.getClass().getMethod("onUnequip").getDeclaringClass() == AbstractRelic.class) {
                    myGoodStuffList.add(r);
                }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        if (myGoodStuffList.isEmpty()) {
            return new Circlet();
        } else {
            myGoodStuffList.removeIf(r -> AbstractDungeon.player.hasRelic(r.relicId));
            return myGoodStuffList.get(AbstractDungeon.cardRandomRng.random(myGoodStuffList.size() - 1));
        }
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}